package com.cezarykluczynski.stapi.etl.template.common.processor.gender;

import com.cezarykluczynski.stapi.etl.template.common.dto.Gender;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class PageToGenderPronounProcessor implements ItemProcessor<Page, Gender> {

	private static final Pattern MALE = Pattern
			.compile("(\\s|\\-)actor|\\she\\s|\\shis\\s|\\shim\\s|himself|stuntman|\\smale", Pattern.CASE_INSENSITIVE);
	private static final Pattern FEMALE = Pattern
			.compile("\\sactress|\\sshe\\s|\\sher\\s|herself|stuntwoman|female", Pattern.CASE_INSENSITIVE);

	@Override
	public Gender process(Page item) throws Exception {
		String firstParagraphs = getFirstParagraphs(item);

		int maleFindings = countFindings(MALE.matcher(firstParagraphs));
		int femaleFindings = countFindings(FEMALE.matcher(firstParagraphs));

		if (maleFindings > 0 && maleFindings > femaleFindings) {
			if (femaleFindings >= 1 && (float) femaleFindings / (float) maleFindings >= .5) {
				log.info("Determined gender {} of {} based on {} male pronouns, but {} female pronoun{} present too",
						Gender.M, item.getTitle(), maleFindings, femaleFindings, femaleFindings == 1 ? " was" : "s were");
			}
			return Gender.M;
		} else if (femaleFindings > 0 && femaleFindings > maleFindings) {
			if (maleFindings >= 1 && (float) maleFindings / (float) femaleFindings >= .5) {
				log.info("Determined gender {} of {} based on {} female pronouns, but {} male pronoun{} present too",
						Gender.F, item.getTitle(), femaleFindings, maleFindings, maleFindings == 1 ? " was" : "s were");
			}
			return Gender.F;
		} else if (femaleFindings == maleFindings && maleFindings > 0) {
			log.warn("Could not determine gender for {}, because equal number of male and female pronouns found ({})",
					item.getTitle(), maleFindings);
			return null;
		}

		return null;
	}

	private String getFirstParagraphs(Page item) {
		List<String> paragraphs = Lists.newArrayList(item.getWikitext().split("\n\n"));
		return paragraphs.isEmpty() ? item.getWikitext() :
				String.join(" ", paragraphs.subList(0, Math.min(3, paragraphs.size())));
	}

	private int countFindings(Matcher matcher) {
		int found = 0;
		while (matcher.find()) {
			found++;
		}
		return found;
	}

}

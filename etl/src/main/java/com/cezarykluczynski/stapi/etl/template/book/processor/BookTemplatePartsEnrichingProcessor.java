package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.company.WikitextToCompaniesProcessor;
import com.cezarykluczynski.stapi.etl.reference.processor.ReferencesFromTemplatePartProcessor;
import com.cezarykluczynski.stapi.etl.template.audio.dto.AudioTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.book_series.processor.WikitextToBookSeriesProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.RunTimeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToStardateRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BookTemplatePartsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, BookTemplate>> {

	private static final String YES = "yes";

	private final BookTemplatePartStaffEnrichingProcessor bookTemplatePartStaffEnrichingProcessor;

	private final WikitextToCompaniesProcessor wikitextToCompaniesProcessor;

	private final BookTemplatePublishedDatesEnrichingProcessor bookTemplatePublishedDatesEnrichingProcessor;

	private final WikitextToYearRangeProcessor wikitextToYearRangeProcessor;

	private final WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor;

	private final ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor;

	private final RunTimeProcessor runTimeProcessor;

	private final WikitextToBookSeriesProcessor wikitextToBookSeriesProcessor;

	@SuppressWarnings("ParameterNumber")
	@Inject
	public BookTemplatePartsEnrichingProcessor(BookTemplatePartStaffEnrichingProcessor bookTemplatePartStaffEnrichingProcessor,
			WikitextToCompaniesProcessor wikitextToCompaniesProcessor,
			BookTemplatePublishedDatesEnrichingProcessor bookTemplatePublishedDatesEnrichingProcessor,
			WikitextToYearRangeProcessor wikitextToYearRangeProcessor, WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor,
			ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor, RunTimeProcessor runTimeProcessor,
			WikitextToBookSeriesProcessor wikitextToBookSeriesProcessor) {
		this.bookTemplatePartStaffEnrichingProcessor = bookTemplatePartStaffEnrichingProcessor;
		this.wikitextToCompaniesProcessor = wikitextToCompaniesProcessor;
		this.bookTemplatePublishedDatesEnrichingProcessor = bookTemplatePublishedDatesEnrichingProcessor;
		this.wikitextToYearRangeProcessor = wikitextToYearRangeProcessor;
		this.wikitextToStardateRangeProcessor = wikitextToStardateRangeProcessor;
		this.referencesFromTemplatePartProcessor = referencesFromTemplatePartProcessor;
		this.runTimeProcessor = runTimeProcessor;
		this.wikitextToBookSeriesProcessor = wikitextToBookSeriesProcessor;
	}

	@Override
	@SuppressWarnings("CyclomaticComplexity")
	public void enrich(EnrichablePair<List<Template.Part>, BookTemplate> enrichablePair) throws Exception {
		BookTemplate bookTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case BookTemplateParameter.AUTHOR:
				case BookTemplateParameter.ARTIST:
				case BookTemplateParameter.EDITOR:
				case BookTemplateParameter.AUDIOBOOK_READ_BY:
					bookTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(part, bookTemplate));
					break;
				case BookTemplateParameter.PUBLISHER:
					bookTemplate.getPublishers().addAll(wikitextToCompaniesProcessor.process(value));
					break;
				case BookTemplateParameter.AUDIOBOOK_PUBLISHER:
					bookTemplate.getAudiobookPublishers().addAll(wikitextToCompaniesProcessor.process(value));
					break;
				case BookTemplateParameter.PUBLISHED:
				case BookTemplateParameter.AUDIOBOOK_PUBLISHED:
					bookTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(part, bookTemplate));
					break;
				case BookTemplateParameter.PAGES:
					bookTemplate.setNumberOfPages(Ints.tryParse(value));
					break;
				case BookTemplateParameter.YEAR:
					YearRange yearRange = wikitextToYearRangeProcessor.process(value);
					if (yearRange != null) {
						bookTemplate.setYearFrom(yearRange.getYearFrom());
						bookTemplate.setYearTo(yearRange.getYearTo());
					}
					break;
				case BookTemplateParameter.STARDATE:
					StardateRange stardateRange = wikitextToStardateRangeProcessor.process(value);
					if (stardateRange != null) {
						bookTemplate.setStardateFrom(stardateRange.getStardateFrom());
						bookTemplate.setStardateTo(stardateRange.getStardateTo());
					}
					break;
				case BookTemplateParameter.SERIES:
					bookTemplate.getBookSeries().addAll(wikitextToBookSeriesProcessor.process(value));
					break;
				case BookTemplateParameter.ISBN:
					bookTemplate.getReferences().addAll(referencesFromTemplatePartProcessor.process(part));
					break;
				case BookTemplateParameter.AUDIOBOOK_ISBN:
					bookTemplate.getAudiobookReferences().addAll(referencesFromTemplatePartProcessor.process(part));
					break;
				case BookTemplateParameter.AUDIOBOOK_RUN_TIME:
				case AudioTemplateParameter.TIME:
					bookTemplate.setAudiobookRunTime(runTimeProcessor.process(value));
					break;
				case BookTemplateParameter.AUDIOBOOK:
					bookTemplate.setAudiobook(Boolean.TRUE.equals(bookTemplate.getAudiobook()) || StringUtils.equalsIgnoreCase(YES, value));
					break;
				case BookTemplateParameter.AUDIOBOOK_ABRIDGED:
					bookTemplate.setAudiobookAbridged(StringUtils.equalsIgnoreCase(YES, value));
					break;
				case BookTemplateParameter.PRODUCTION:
					bookTemplate.setProductionNumber(value);
					break;
				default:
					break;
			}
		}
	}

}

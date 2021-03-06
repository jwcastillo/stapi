package com.cezarykluczynski.stapi.etl.template.starship.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.etl.template.starship.service.StarshipPageFilter;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StarshipTemplatePageProcessor implements ItemProcessor<Page, StarshipTemplate> {

	private final StarshipPageFilter starshipPageFilter;

	private final TemplateFinder templateFinder;

	private final PageBindingService pageBindingService;

	private final StarshipTemplateCompositeEnrichingProcessor starshipTemplateCompositeEnrichingProcessor;

	public StarshipTemplatePageProcessor(StarshipPageFilter starshipPageFilter, TemplateFinder templateFinder, PageBindingService pageBindingService,
			StarshipTemplateCompositeEnrichingProcessor starshipTemplateCompositeEnrichingProcessor) {
		this.starshipPageFilter = starshipPageFilter;
		this.templateFinder = templateFinder;
		this.pageBindingService = pageBindingService;
		this.starshipTemplateCompositeEnrichingProcessor = starshipTemplateCompositeEnrichingProcessor;
	}

	@Override
	public StarshipTemplate process(Page item) throws Exception {
		if (starshipPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		StarshipTemplate starshipTemplate = new StarshipTemplate();
		starshipTemplate.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		starshipTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_STARSHIP);

		if (templateOptional.isPresent()) {
			starshipTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(templateOptional.get(), starshipTemplate));
		}

		return starshipTemplate;
	}

}

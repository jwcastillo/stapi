package com.cezarykluczynski.stapi.etl.template.starship.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

@Service
public class StarshipPageFilter implements MediaWikiPageFilter {

	private static final String UNNAMED_PREFIX = "Unnamed";

	private static final Set<String> INVALID_TITLES = Sets.newHashSet("Unidentified flying object");

	private final CategorySortingService categorySortingService;

	@Inject
	public StarshipPageFilter(CategorySortingService categorySortingService) {
		this.categorySortingService = categorySortingService;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		String pageTitle = page.getTitle();
		return !page.getRedirectPath().isEmpty() || categorySortingService.isSortedOnTopOfAnyCategory(page) || INVALID_TITLES.contains(pageTitle)
				|| pageTitle.startsWith(UNNAMED_PREFIX);
	}

}

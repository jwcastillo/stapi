package com.cezarykluczynski.stapi.model.episode.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.episode.entity.Episode_;
import com.cezarykluczynski.stapi.model.episode.query.EpisodeQueryBuilderFactory;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Repository
public class EpisodeRepositoryImpl extends AbstractRepositoryImpl<Episode> implements EpisodeRepositoryCustom {

	private EpisodeQueryBuilderFactory episodeQueryBuilderFactory;

	@Inject
	public EpisodeRepositoryImpl(EpisodeQueryBuilderFactory episodeQueryBuilderFactory) {
		this.episodeQueryBuilderFactory = episodeQueryBuilderFactory;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Episode> findMatching(EpisodeRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Episode> episodeQueryBuilder = episodeQueryBuilderFactory.createQueryBuilder(pageable);
		String guid = criteria.getGuid();
		boolean doFetch = guid != null;

		episodeQueryBuilder.equal(Episode_.guid, guid);
		episodeQueryBuilder.like(Episode_.title, criteria.getTitle());
		episodeQueryBuilder.like(Episode_.productionSerialNumber, criteria.getProductionSerialNumber());
		episodeQueryBuilder.between(Episode_.seasonNumber, criteria.getSeasonNumberFrom(),
				criteria.getSeasonNumberTo());
		episodeQueryBuilder.between(Episode_.episodeNumber, criteria.getEpisodeNumberFrom(),
				criteria.getEpisodeNumberTo());
		episodeQueryBuilder.between(Episode_.year, criteria.getYearFrom(), criteria.getYearTo());
		episodeQueryBuilder.between(Episode_.stardate, criteria.getStardateFrom(), criteria.getStardateTo());
		episodeQueryBuilder.equal(Episode_.featureLength, criteria.getFeatureLength());
		episodeQueryBuilder.between(Episode_.usAirDate, criteria.getUsAirDateFrom(), criteria.getUsAirDateTo());
		episodeQueryBuilder.between(Episode_.finalScriptDate, criteria.getFinalScriptDateFrom(),
				criteria.getFinalScriptDateTo());
		episodeQueryBuilder.setOrder(criteria.getOrder());
		episodeQueryBuilder.fetch(Episode_.writers, doFetch);
		episodeQueryBuilder.fetch(Episode_.teleplayAuthors, doFetch);
		episodeQueryBuilder.fetch(Episode_.storyAuthors, doFetch);
		episodeQueryBuilder.fetch(Episode_.directors, doFetch);
		episodeQueryBuilder.fetch(Episode_.staff, doFetch);
		episodeQueryBuilder.fetch(Episode_.performers, doFetch);
		episodeQueryBuilder.fetch(Episode_.stuntPerformers, doFetch);
		episodeQueryBuilder.fetch(Episode_.standInPerformers, doFetch);

		Page<Episode> episodePage = episodeQueryBuilder.findPage();
		clearProxies(episodePage, !doFetch);
		return episodePage;
	}

	@Override
	protected void clearProxies(Page<Episode> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(episode -> {
			episode.setWriters(Sets.newHashSet());
			episode.setTeleplayAuthors(Sets.newHashSet());
			episode.setStoryAuthors(Sets.newHashSet());
			episode.setDirectors(Sets.newHashSet());
			episode.setStaff(Sets.newHashSet());
			episode.setPerformers(Sets.newHashSet());
			episode.setStuntPerformers(Sets.newHashSet());
			episode.setStandInPerformers(Sets.newHashSet());
		});
	}

}

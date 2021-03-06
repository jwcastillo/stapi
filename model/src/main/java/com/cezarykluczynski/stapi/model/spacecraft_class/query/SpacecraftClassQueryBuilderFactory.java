package com.cezarykluczynski.stapi.model.spacecraft_class.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SpacecraftClassQueryBuilderFactory extends AbstractQueryBuilderFactory<SpacecraftClass> {

	@Inject
	public SpacecraftClassQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, SpacecraftClass.class);
	}

}

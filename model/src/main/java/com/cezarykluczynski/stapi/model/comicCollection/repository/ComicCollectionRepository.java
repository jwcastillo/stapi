package com.cezarykluczynski.stapi.model.comicCollection.repository;

import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComicCollectionRepository extends JpaRepository<ComicCollection, Long>, ComicCollectionRepositoryCustom {

	Optional<ComicCollection> findByPageTitleAndPageMediaWikiSource(String title, MediaWikiSource mediaWikiSource);

}

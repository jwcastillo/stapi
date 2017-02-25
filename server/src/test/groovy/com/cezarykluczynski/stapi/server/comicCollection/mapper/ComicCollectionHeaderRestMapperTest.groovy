package com.cezarykluczynski.stapi.server.comicCollection.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionHeader
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicCollectionHeaderRestMapperTest extends AbstractComicCollectionMapperTest {

	private ComicCollectionHeaderRestMapper comicCollectionHeaderRestMapper

	void setup() {
		comicCollectionHeaderRestMapper = Mappers.getMapper(ComicCollectionHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		ComicCollection comicCollection = new ComicCollection(
				guid: GUID,
				title: TITLE)

		when:
		ComicCollectionHeader comicCollectionHeader = comicCollectionHeaderRestMapper.map(Lists.newArrayList(comicCollection))[0]

		then:
		comicCollectionHeader.guid == GUID
		comicCollectionHeader.title == TITLE
	}

}

package com.cezarykluczynski.stapi.model.performer.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestOrderDTO
import com.cezarykluczynski.stapi.model.common.entity.Gender
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.query.PerformerQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractRealWorldPersonTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class PerformerRepositoryImplTest extends AbstractRealWorldPersonTest {

	private static final Gender GENDER = Gender.F
	private static final RequestOrderDTO ORDER = new RequestOrderDTO()
	private static final String CHARACTERS = 'characters'

	private PerformerQueryBuilderFactory performerQueryBuilderMock

	private PerformerRepositoryImpl performerRepositoryImpl

	private QueryBuilder<Performer> performerQueryBuilder

	private Pageable pageable

	private PerformerRequestDTO performerRequestDTO

	private Performer performer

	private Page page

	def setup() {
		performerQueryBuilderMock = Mock(PerformerQueryBuilderFactory)
		performerRepositoryImpl = new PerformerRepositoryImpl(performerQueryBuilderMock)
		performerQueryBuilder = Mock(QueryBuilder)
		pageable = Mock(Pageable)
		performerRequestDTO = Mock(PerformerRequestDTO)
		page = Mock(Page)
		performer = Mock(Performer)
	}

	def "query is built and performed"() {
		when:
		Page pageOutput = performerRepositoryImpl.findMatching(performerRequestDTO, pageable)

		then:
		1 * performerQueryBuilderMock.createQueryBuilder(pageable) >> performerQueryBuilder

		then: 'guid criteria is set'
		1 * performerRequestDTO.getGuid() >> GUID
		1 * performerQueryBuilder.equal("guid", GUID)

		then: 'string criteria are set'
		1 * performerRequestDTO.getName() >> NAME
		1 * performerQueryBuilder.like("name", NAME)
		1 * performerRequestDTO.getBirthName() >> BIRTH_NAME
		1 * performerQueryBuilder.like("birthName", BIRTH_NAME)
		1 * performerRequestDTO.getPlaceOfBirth() >> PLACE_OF_BIRTH
		1 * performerQueryBuilder.like("placeOfBirth", PLACE_OF_BIRTH)
		1 * performerRequestDTO.getPlaceOfDeath() >> PLACE_OF_DEATH
		1 * performerQueryBuilder.like("placeOfDeath", PLACE_OF_DEATH)

		then: 'date criteria are set'
		1 * performerRequestDTO.getDateOfBirthFrom() >> DATE_OF_BIRTH_FROM
		1 * performerRequestDTO.getDateOfBirthTo() >> DATE_OF_BIRTH_TO
		1 * performerQueryBuilder.between("dateOfBirth", DATE_OF_BIRTH_FROM, DATE_OF_BIRTH_TO)
		1 * performerRequestDTO.getDateOfDeathFrom() >> DATE_OF_DEATH_FROM
		1 * performerRequestDTO.getDateOfDeathTo() >> DATE_OF_DEATH_TO
		1 * performerQueryBuilder.between("dateOfDeath", DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO)

		then: 'enum criteria is set'
		1 * performerRequestDTO.getGender() >> GENDER
		1 * performerQueryBuilder.equal("gender", GENDER)

		then: 'boolean criteria are set'
		1 * performerRequestDTO.getAnimalPerformer() >> ANIMAL_PERFORMER
		1 * performerQueryBuilder.equal("animalPerformer", ANIMAL_PERFORMER)
		1 * performerRequestDTO.getDisPerformer() >> DIS_PERFORMER
		1 * performerQueryBuilder.equal("disPerformer", DIS_PERFORMER)
		1 * performerRequestDTO.getDs9Performer() >> DS9_PERFORMER
		1 * performerQueryBuilder.equal("ds9Performer", DS9_PERFORMER)
		1 * performerRequestDTO.getEntPerformer() >> ENT_PERFORMER
		1 * performerQueryBuilder.equal("entPerformer", ENT_PERFORMER)
		1 * performerRequestDTO.getFilmPerformer() >> FILM_PERFORMER
		1 * performerQueryBuilder.equal("filmPerformer", FILM_PERFORMER)
		1 * performerRequestDTO.getStandInPerformer() >> STAND_IN_PERFORMER
		1 * performerQueryBuilder.equal("standInPerformer", STAND_IN_PERFORMER)
		1 * performerRequestDTO.getStuntPerformer() >> STUNT_PERFORMER
		1 * performerQueryBuilder.equal("stuntPerformer", STUNT_PERFORMER)
		1 * performerRequestDTO.getTasPerformer() >> TAS_PERFORMER
		1 * performerQueryBuilder.equal("tasPerformer", TAS_PERFORMER)
		1 * performerRequestDTO.getTngPerformer() >> TNG_PERFORMER
		1 * performerQueryBuilder.equal("tngPerformer", TNG_PERFORMER)
		1 * performerRequestDTO.getTosPerformer() >> TOS_PERFORMER
		1 * performerQueryBuilder.equal("tosPerformer", TOS_PERFORMER)
		1 * performerRequestDTO.getVideoGamePerformer() >> VIDEO_GAME_PERFORMER
		1 * performerQueryBuilder.equal("videoGamePerformer", VIDEO_GAME_PERFORMER)
		1 * performerRequestDTO.getVoicePerformer() >> VOICE_PERFORMER
		1 * performerQueryBuilder.equal("voicePerformer", VOICE_PERFORMER)
		1 * performerRequestDTO.getVoyPerformer() >> VOY_PERFORMER
		1 * performerQueryBuilder.equal("voyPerformer", VOY_PERFORMER)

		then: 'order is set'
		1 * performerRequestDTO.getOrder() >> ORDER
		1 * performerQueryBuilder.setOrder(ORDER)

		then: 'fetch is performed with true flag'
		1 * performerQueryBuilder.fetch(CHARACTERS, true)

		then: 'page is searched for and returned'
		1 * performerQueryBuilder.findPage() >> page
		0 * page.getContent()
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	def "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = performerRepositoryImpl.findMatching(performerRequestDTO, pageable)

		then:
		1 * performerQueryBuilderMock.createQueryBuilder(pageable) >> performerQueryBuilder

		then: 'guid criteria is set to null'
		1 * performerRequestDTO.getGuid() >> null

		then: 'fetch is performed with false flag'
		1 * performerQueryBuilder.fetch(CHARACTERS, false)

		then: 'page is searched for and returned'
		1 * performerQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.getContent() >> Lists.newArrayList(performer)
		1 * performer.setPerformances(Sets.newHashSet())
		1 * performer.setStuntPerformances(Sets.newHashSet())
		1 * performer.setStandInPerformances(Sets.newHashSet())
		1 * performer.setCharacters(Sets.newHashSet())
		pageOutput == page
	}

}
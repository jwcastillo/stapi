package com.cezarykluczynski.stapi.server.company.mapper

import com.cezarykluczynski.stapi.client.v1.soap.CompanyBase
import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFull
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullRequest
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class CompanySoapMapperTest extends AbstractCompanyMapperTest {

	private CompanySoapMapper companySoapMapper

	void setup() {
		companySoapMapper = Mappers.getMapper(CompanySoapMapper)
	}

	void "maps SOAP CompanyBaseRequest to CompanyRequestDTO"() {
		given:
		CompanyBaseRequest companyBaseRequest = new CompanyBaseRequest(
				name: NAME,
				broadcaster: BROADCASTER,
				collectibleCompany: COLLECTIBLE_COMPANY,
				conglomerate: CONGLOMERATE,
				digitalVisualEffectsCompany: DIGITAL_VISUAL_EFFECTS_COMPANY,
				distributor: DISTRIBUTOR,
				gameCompany: GAME_COMPANY,
				filmEquipmentCompany: FILM_EQUIPMENT_COMPANY,
				makeUpEffectsStudio: MAKE_UP_EFFECTS_STUDIO,
				mattePaintingCompany: MATTE_PAINTING_COMPANY,
				modelAndMiniatureEffectsCompany: MODEL_AND_MINIATURE_EFFECTS_COMPANY,
				postProductionCompany: POST_PRODUCTION_COMPANY,
				productionCompany: PRODUCTION_COMPANY,
				propCompany: PROP_COMPANY,
				recordLabel: RECORD_LABEL,
				specialEffectsCompany: SPECIAL_EFFECTS_COMPANY,
				tvAndFilmProductionCompany: TV_AND_FILM_PRODUCTION_COMPANY,
				videoGameCompany: VIDEO_GAME_COMPANY)

		when:
		CompanyRequestDTO companyRequestDTO = companySoapMapper.mapBase companyBaseRequest

		then:
		companyRequestDTO.name == NAME
		companyRequestDTO.broadcaster == BROADCASTER
		companyRequestDTO.collectibleCompany == COLLECTIBLE_COMPANY
		companyRequestDTO.conglomerate == CONGLOMERATE
		companyRequestDTO.digitalVisualEffectsCompany == DIGITAL_VISUAL_EFFECTS_COMPANY
		companyRequestDTO.distributor == DISTRIBUTOR
		companyRequestDTO.gameCompany == GAME_COMPANY
		companyRequestDTO.filmEquipmentCompany == FILM_EQUIPMENT_COMPANY
		companyRequestDTO.makeUpEffectsStudio == MAKE_UP_EFFECTS_STUDIO
		companyRequestDTO.mattePaintingCompany == MATTE_PAINTING_COMPANY
		companyRequestDTO.modelAndMiniatureEffectsCompany == MODEL_AND_MINIATURE_EFFECTS_COMPANY
		companyRequestDTO.postProductionCompany == POST_PRODUCTION_COMPANY
		companyRequestDTO.productionCompany == PRODUCTION_COMPANY
		companyRequestDTO.propCompany == PROP_COMPANY
		companyRequestDTO.recordLabel == RECORD_LABEL
		companyRequestDTO.specialEffectsCompany == SPECIAL_EFFECTS_COMPANY
		companyRequestDTO.tvAndFilmProductionCompany == TV_AND_FILM_PRODUCTION_COMPANY
		companyRequestDTO.videoGameCompany == VIDEO_GAME_COMPANY
	}

	void "maps SOAP CompanyFullRequest to CompanyBaseRequestDTO"() {
		given:
		CompanyFullRequest companyFullRequest = new CompanyFullRequest(guid: GUID)

		when:
		CompanyRequestDTO companyRequestDTO = companySoapMapper.mapFull companyFullRequest

		then:
		companyRequestDTO.guid == GUID
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Company company = createCompany()

		when:
		CompanyBase companyBase = companySoapMapper.mapBase(Lists.newArrayList(company))[0]

		then:
		companyBase.guid == GUID
		companyBase.name == NAME
		companyBase.broadcaster == BROADCASTER
		companyBase.collectibleCompany == COLLECTIBLE_COMPANY
		companyBase.conglomerate == CONGLOMERATE
		companyBase.digitalVisualEffectsCompany == DIGITAL_VISUAL_EFFECTS_COMPANY
		companyBase.distributor == DISTRIBUTOR
		companyBase.gameCompany == GAME_COMPANY
		companyBase.filmEquipmentCompany == FILM_EQUIPMENT_COMPANY
		companyBase.makeUpEffectsStudio == MAKE_UP_EFFECTS_STUDIO
		companyBase.mattePaintingCompany == MATTE_PAINTING_COMPANY
		companyBase.modelAndMiniatureEffectsCompany == MODEL_AND_MINIATURE_EFFECTS_COMPANY
		companyBase.postProductionCompany == POST_PRODUCTION_COMPANY
		companyBase.productionCompany == PRODUCTION_COMPANY
		companyBase.propCompany == PROP_COMPANY
		companyBase.recordLabel == RECORD_LABEL
		companyBase.specialEffectsCompany == SPECIAL_EFFECTS_COMPANY
		companyBase.tvAndFilmProductionCompany == TV_AND_FILM_PRODUCTION_COMPANY
		companyBase.videoGameCompany == VIDEO_GAME_COMPANY
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Company company = createCompany()

		when:
		CompanyFull companyFull = companySoapMapper.mapFull(company)

		then:
		companyFull.guid == GUID
		companyFull.name == NAME
		companyFull.broadcaster == BROADCASTER
		companyFull.collectibleCompany == COLLECTIBLE_COMPANY
		companyFull.conglomerate == CONGLOMERATE
		companyFull.digitalVisualEffectsCompany == DIGITAL_VISUAL_EFFECTS_COMPANY
		companyFull.distributor == DISTRIBUTOR
		companyFull.gameCompany == GAME_COMPANY
		companyFull.filmEquipmentCompany == FILM_EQUIPMENT_COMPANY
		companyFull.makeUpEffectsStudio == MAKE_UP_EFFECTS_STUDIO
		companyFull.mattePaintingCompany == MATTE_PAINTING_COMPANY
		companyFull.modelAndMiniatureEffectsCompany == MODEL_AND_MINIATURE_EFFECTS_COMPANY
		companyFull.postProductionCompany == POST_PRODUCTION_COMPANY
		companyFull.productionCompany == PRODUCTION_COMPANY
		companyFull.propCompany == PROP_COMPANY
		companyFull.recordLabel == RECORD_LABEL
		companyFull.specialEffectsCompany == SPECIAL_EFFECTS_COMPANY
		companyFull.tvAndFilmProductionCompany == TV_AND_FILM_PRODUCTION_COMPANY
		companyFull.videoGameCompany == VIDEO_GAME_COMPANY
	}

}

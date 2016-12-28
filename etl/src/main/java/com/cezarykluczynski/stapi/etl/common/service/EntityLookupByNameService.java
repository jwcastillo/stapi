package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;
import java.util.Optional;

@Service
@Slf4j
public class EntityLookupByNameService {

	private PageApi pageApi;

	private CharacterRepository characterRepository;

	private PerformerRepository performerRepository;

	private StaffRepository staffRepository;

	@Inject
	public EntityLookupByNameService(PageApi pageApi, CharacterRepository characterRepository,
			PerformerRepository performerRepository, StaffRepository staffRepository) {
		this.pageApi = pageApi;
		this.characterRepository = characterRepository;
		this.performerRepository = performerRepository;
		this.staffRepository = staffRepository;
	}

	public Optional<Performer> findPerformerByName(String performerName, MediaWikiSource mediaWikiSource) {
		Optional<Performer> performerOptional;

		try {
			performerOptional = performerRepository.findByName(performerName);
		} catch (NonUniqueResultException e) {
			performerOptional = Optional.empty();
		}

		if (performerOptional.isPresent()) {
			return performerOptional;
		} else {
			Page page = pageApi.getPage(performerName, mediaWikiSource);
			if (page != null) {
				return performerRepository.findByPagePageId(page.getPageId());
			}
		}

		return Optional.empty();
	}

	public Optional<Character> findCharacterByName(String characterName, MediaWikiSource mediaWikiSource) {
		Optional<Character> characterOptional;

		try {
			characterOptional = characterRepository.findByName(characterName);
		} catch (NonUniqueResultException e) {
			characterOptional = Optional.empty();
		}

		if (characterOptional.isPresent()) {
			return characterOptional;
		} else {
			Page page = pageApi.getPage(characterName, mediaWikiSource);
			if (page != null) {
				return characterRepository.findByPagePageId(page.getPageId());
			}
		}

		return Optional.empty();
	}

	public Optional<Staff> findStaffByName(String staffName, MediaWikiSource mediaWikiSource) {
		Optional<Staff> staffOptional;

		try {
			staffOptional = staffRepository.findByName(staffName);
		} catch (NonUniqueResultException e) {
			staffOptional = Optional.empty();
		}

		if (staffOptional.isPresent()) {
			return staffOptional;
		} else {
			Page page = pageApi.getPage(staffName, mediaWikiSource);
			if (page != null) {
				return staffRepository.findByPagePageId(page.getPageId());
			}
		}

		return Optional.empty();
	}

}
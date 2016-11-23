package com.cezarykluczynski.stapi.server.performer.mapper;

import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class, CharacterHeaderRestMapper.class})
public interface PerformerRestMapper {

	@Mappings({
			@Mapping(source = "characters", target = "characterHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.rest.model.Performer map(Performer series);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.Performer> map(List<Performer> series);

}
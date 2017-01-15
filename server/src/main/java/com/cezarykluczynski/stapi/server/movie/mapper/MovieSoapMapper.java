package com.cezarykluczynski.stapi.server.movie.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.MovieRequest;
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderSoapMapper.class, DateMapper.class,
		EnumMapper.class, RequestSortSoapMapper.class, PerformerHeaderSoapMapper.class, StaffHeaderSoapMapper.class})
public interface MovieSoapMapper {

	@Mappings({
			@Mapping(source = "stardate.from", target = "stardateFrom"),
			@Mapping(source = "stardate.to", target = "stardateTo"),
			@Mapping(source = "year.from", target = "yearFrom"),
			@Mapping(source = "year.to", target = "yearTo"),
			@Mapping(source = "usReleaseDate.from", target = "usReleaseDateFrom"),
			@Mapping(source = "usReleaseDate.to", target = "usReleaseDateTo")
	})
	MovieRequestDTO map(MovieRequest movieRequest);

	@Mappings({
			@Mapping(source = "writers", target = "writerHeaders"),
			@Mapping(source = "screenplayAuthors", target = "screenplayAuthorHeaders"),
			@Mapping(source = "storyAuthors", target = "storyAuthorHeaders"),
			@Mapping(source = "directors", target = "directorHeaders"),
			@Mapping(source = "producers", target = "producerHeaders"),
			@Mapping(source = "performers", target = "performerHeaders"),
			@Mapping(source = "staff", target = "staffHeaders"),
			@Mapping(source = "stuntPerformers", target = "stuntPerformerHeaders"),
			@Mapping(source = "standInPerformers", target = "standInPerformerHeaders"),
			@Mapping(source = "characters", target = "characterHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.soap.Movie map(Movie movie);

	List<com.cezarykluczynski.stapi.client.v1.soap.Movie> map(List<Movie> movieList);

}
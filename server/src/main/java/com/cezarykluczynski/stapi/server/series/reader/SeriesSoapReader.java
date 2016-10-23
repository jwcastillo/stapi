package com.cezarykluczynski.stapi.server.series.reader;

import com.cezarykluczynski.stapi.client.soap.SeriesRequest;
import com.cezarykluczynski.stapi.client.soap.SeriesResponse;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesSoapMapper;
import com.cezarykluczynski.stapi.server.series.query.SeriesQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SeriesSoapReader implements Reader<SeriesRequest, SeriesResponse> {

	private SeriesQueryBuilder seriesQueryBuilder;

	private SeriesSoapMapper seriesSoapMapper;

	private PageMapper pageMapper;

	@Inject
	public SeriesSoapReader(SeriesQueryBuilder seriesQueryBuilder, SeriesSoapMapper seriesSoapMapper, PageMapper pageMapper) {
		this.seriesQueryBuilder = seriesQueryBuilder;
		this.seriesSoapMapper = seriesSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public SeriesResponse read(SeriesRequest seriesRequest) {
		Page<Series> seriesPage = seriesQueryBuilder.query(seriesRequest);
		SeriesResponse seriesResponse = new SeriesResponse();
		seriesResponse.setPage(pageMapper.toResponsePage(seriesPage));
		seriesResponse.getSeries().addAll(seriesSoapMapper.map(seriesPage.getContent()));
		return seriesResponse;
	}

}

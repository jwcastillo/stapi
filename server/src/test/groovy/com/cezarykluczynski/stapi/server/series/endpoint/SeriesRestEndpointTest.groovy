package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams
import com.cezarykluczynski.stapi.server.series.reader.SeriesRestReader

class SeriesRestEndpointTest extends AbstractRestEndpointTest {

	private static final String GUID = 'GUID'
	private static final String TITLE = 'TITLE'

	private SeriesRestReader seriesRestReaderMock

	private SeriesRestEndpoint seriesRestEndpoint

	void setup() {
		seriesRestReaderMock = Mock(SeriesRestReader)
		seriesRestEndpoint = new SeriesRestEndpoint(seriesRestReaderMock)
	}

	void "passes get call to SeriesRestReader"() {
		given:
		SeriesFullResponse seriesFullResponse = Mock(SeriesFullResponse)

		when:
		SeriesFullResponse seriesFullResponseOutput = seriesRestEndpoint.getSeries(GUID)

		then:
		1 * seriesRestReaderMock.readFull(GUID) >> seriesFullResponse
		seriesFullResponseOutput == seriesFullResponse
	}

	void "passes search get call to SeriesRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		SeriesBaseResponse seriesResponse = Mock(SeriesBaseResponse)

		when:
		SeriesBaseResponse seriesResponseOutput = seriesRestEndpoint.searchSeries(pageAwareBeanParams)

		then:
		1 * seriesRestReaderMock.readBase(_ as SeriesRestBeanParams) >> { SeriesRestBeanParams seriesRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			seriesResponse
		}
		seriesResponseOutput == seriesResponse
	}

	void "passes search post call to SeriesRestReader"() {
		given:
		SeriesRestBeanParams seriesRestBeanParams = new SeriesRestBeanParams(title: TITLE)
		SeriesBaseResponse seriesResponse = Mock(SeriesBaseResponse)

		when:
		SeriesBaseResponse seriesResponseOutput = seriesRestEndpoint.searchSeries(seriesRestBeanParams)

		then:
		1 * seriesRestReaderMock.readBase(seriesRestBeanParams as SeriesRestBeanParams) >> { SeriesRestBeanParams params ->
			assert params.title == TITLE
			seriesResponse
		}
		seriesResponseOutput == seriesResponse
	}

}

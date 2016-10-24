package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.soap.RequestPage
import com.cezarykluczynski.stapi.client.soap.SeriesRequest
import com.cezarykluczynski.stapi.client.soap.SeriesResponse
import com.cezarykluczynski.stapi.server.series.common.EndpointIntegrationTest

class SeriesSoapEndpointIntegrationTest extends EndpointIntegrationTest {

	def setup() {
		createSoapClient()
	}

	def "gets all series"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		SeriesResponse seriesResponse = stapiSoapClient.seriesPortType.getSeries(new SeriesRequest(
				page: new RequestPage(
						pageNumber: pageNumber,
						pageSize: pageSize
				)
		))

		then:
		seriesResponse.page.pageNumber == pageNumber
		seriesResponse.page.pageSize == pageSize
		seriesResponse.series.size() == 6

	}

	def "gets series by title"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 2

		when:
		SeriesResponse seriesResponse = stapiSoapClient.seriesPortType.getSeries(new SeriesRequest(
				page: new RequestPage(
						pageNumber: pageNumber,
						pageSize: pageSize
				),
				title: "Voyager"
		))

		then:
		seriesResponse.page.pageNumber == pageNumber
		seriesResponse.page.pageSize == pageSize
		seriesResponse.series.size() == 1
	}

}
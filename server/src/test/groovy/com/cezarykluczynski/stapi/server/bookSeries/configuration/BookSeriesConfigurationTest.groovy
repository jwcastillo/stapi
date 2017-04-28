package com.cezarykluczynski.stapi.server.bookSeries.configuration

import com.cezarykluczynski.stapi.server.bookSeries.endpoint.BookSeriesSoapEndpoint
import com.cezarykluczynski.stapi.server.bookSeries.mapper.BookSeriesBaseRestMapper
import com.cezarykluczynski.stapi.server.bookSeries.mapper.BookSeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.bookSeries.mapper.BookSeriesFullRestMapper
import com.cezarykluczynski.stapi.server.bookSeries.mapper.BookSeriesFullSoapMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import spock.lang.Specification

import javax.xml.ws.Endpoint

class BookSeriesConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private BookSeriesConfiguration bookSeriesConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		bookSeriesConfiguration = new BookSeriesConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "BookSeries SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = bookSeriesConfiguration.bookSeriesEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(BookSeriesSoapEndpoint, BookSeriesSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "BookSeriesBaseSoapMapper is created"() {
		when:
		BookSeriesBaseSoapMapper bookSeriesBaseSoapMapper = bookSeriesConfiguration.bookSeriesBaseSoapMapper()

		then:
		bookSeriesBaseSoapMapper != null
	}

	void "BookSeriesFullSoapMapper is created"() {
		when:
		BookSeriesFullSoapMapper bookSeriesFullSoapMapper = bookSeriesConfiguration.bookSeriesFullSoapMapper()

		then:
		bookSeriesFullSoapMapper != null
	}

	void "BookSeriesBaseRestMapper is created"() {
		when:
		BookSeriesBaseRestMapper bookSeriesBaseRestMapper = bookSeriesConfiguration.bookSeriesBaseRestMapper()

		then:
		bookSeriesBaseRestMapper != null
	}

	void "BookSeriesFullRestMapper is created"() {
		when:
		BookSeriesFullRestMapper bookSeriesFullRestMapper = bookSeriesConfiguration.bookSeriesFullRestMapper()

		then:
		bookSeriesFullRestMapper != null
	}

}

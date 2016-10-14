package com.cezarykluczynski.stapi.client.api

class StapiSoapPortTypesProviderTest extends AbstractStapiClientTest {

	def "provider can be instantiated with canonical URL"() {
		when:
		StapiSoapPortTypesProvider stapiSoapPortTypesProvider = new StapiSoapPortTypesProvider()

		then:
		((String) toBindingProvider(stapiSoapPortTypesProvider.seriesPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
	}

	def "provider can be instantiated with custom url"() {
		when:
		StapiSoapPortTypesProvider stapiSoapPortTypesProvider = new StapiSoapPortTypesProvider(CUSTOM_URL)

		then:
		((String) toBindingProvider(stapiSoapPortTypesProvider.seriesPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
	}

}

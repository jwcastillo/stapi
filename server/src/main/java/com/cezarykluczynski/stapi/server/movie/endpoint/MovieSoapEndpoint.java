package com.cezarykluczynski.stapi.server.movie.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MoviePortType;
import com.cezarykluczynski.stapi.server.movie.reader.MovieSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class MovieSoapEndpoint implements MoviePortType {

	private MovieSoapReader movieSoapReader;

	public MovieSoapEndpoint(MovieSoapReader movieSoapReader) {
		this.movieSoapReader = movieSoapReader;
	}

	@Override
	public MovieBaseResponse getMovieBase(@WebParam(partName = "request", name = "MovieBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/movie") MovieBaseRequest request) {
		return movieSoapReader.readBase(request);
	}

	@Override
	public MovieFullResponse getMovieFull(@WebParam(partName = "request", name = "MovieFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/movie") MovieFullRequest request) {
		return movieSoapReader.readFull(request);
	}

}

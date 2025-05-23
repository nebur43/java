package com.ejercicio.ejerciciopractico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import https.spring_io.guides.gs.producing_web_service.GetCountryRequest;
import https.spring_io.guides.gs.producing_web_service.GetCountryResponse;


@Endpoint
public class CountryEndpoint {
//	private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
	private static final String NAMESPACE_URI = "https://spring.io/guides/gs/producing-web-service/";

	private CountryRepository countryRepository;

	@Autowired
	public CountryEndpoint(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
	@ResponsePayload
	public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
		GetCountryResponse response = new GetCountryResponse();
		response.setCountry(countryRepository.findCountry(request.getName()));

		return response;
	}
}

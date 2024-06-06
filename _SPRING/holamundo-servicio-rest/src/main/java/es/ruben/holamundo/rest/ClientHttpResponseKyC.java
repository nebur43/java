package es.ruben.holamundo.rest;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

public class ClientHttpResponseKyC implements ClientHttpResponse{

	private ClientHttpResponse clientHttpResponse;
	
	private InputStream body;
	
	public ClientHttpResponseKyC(ClientHttpResponse clientHttpResponse) {
		this.clientHttpResponse = clientHttpResponse;
	}
	
	public void setBody(InputStream body) {
		this.body = body;
	}
	
	@Override
	public InputStream getBody() throws IOException {
		if (this.body!=null) {
			return this.body;
		}
		return this.clientHttpResponse.getBody();
	}

	@Override
	public HttpHeaders getHeaders() {
		return this.clientHttpResponse.getHeaders();	
	}

	@Override
	public HttpStatus getStatusCode() throws IOException {
		return this.clientHttpResponse.getStatusCode();
	}

	@Override
	public int getRawStatusCode() throws IOException {
		return this.clientHttpResponse.getRawStatusCode();
	}

	@Override
	public String getStatusText() throws IOException {
		return this.clientHttpResponse.getStatusText();
	}

	@Override
	public void close() {
		try {
			this.body.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.clientHttpResponse.close();		
	}

}

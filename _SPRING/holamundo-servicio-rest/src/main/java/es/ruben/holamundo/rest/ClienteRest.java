package es.ruben.holamundo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import es.ruben.holamundo.rest.dao.Persona;
import es.ruben.holamundo.rest.dao.RequestHolaMundo;
import es.ruben.holamundo.rest.dao.ResponseHolaMundo;

public class ClienteRest {

	public static void main(String[] args) {

//		Persona pareja = new Persona("maria"," pepita" , 45, 55.1);
		Persona persona = new Persona("paco", "perez perez", 50, 101.1);
		
		// ******** getForObject **********
		
		
		RestTemplate restTemplate = new RestTemplate();
//		restTemplate.exchange("http://localhost:8080/holamundo-servicio-rest/getTrabajadores", HttpMethod.GET,Persona.class);
		String s = restTemplate.getForObject("http://localhost:8080/holamundo-servicio-rest/getTrabajadores", String.class);
		System.out.println("Devolucion 01:" + s);
		
		s = restTemplate.getForObject("http://localhost:8080/holamundo-servicio-rest/getTrabajadores", String.class,"nombre","superman");
		System.out.println("Devolucion 02:" + s);
		
		 Map<String, String> params = new HashMap<String, String>();
		 params.put("name", "superman");
		 s = restTemplate.getForObject("http://localhost:8080/holamundo-servicio-rest/getTrabajadores?nombre={name}", String.class,params);
		 System.out.println("Devolucion 03:" + s);
		 
		 
		 
		 
//		 List<Persona> personas = restTemplate.getForObject("http://localhost:8080/holamundo-servicio-rest/getTrabajadores", List<Persona>,"nombre","superman");
		 
		 /*
		  * 
		  * 		HttpEntity<CancelProcessRequestBean> request = new HttpEntity<CancelProcessRequestBean>(requestBean, null);
		CancelProcessResponseBean cancelProcessResponseBean = restTemplate.postForObject(endpoint + CANCELPROCESS_METHOD, request, CancelProcessResponseBean.class);
		return cancelProcessResponseBean;
		  * 
		  * 
		  */
		 
		 
//		 s = restTemplate.getForObject("http://localhost:8080/holamundo-servicio-rest/getKK", String.class,params);
//		 System.out.println("Devoluci�n 04:" + s);
		 
				
		// ******** exchange **********		
		
		HttpHeaders headers = new HttpHeaders();
//		headers.set("Accept", MediaType.A);

		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/holamundo-servicio-rest/getTrabajadores")
		        .queryParam("nombre", "superman");

		HttpEntity<?> entity = new HttpEntity(headers);

		HttpEntity<String> response = restTemplate.exchange(
		        builder.toUriString(), 
		        HttpMethod.GET, 
		        entity, 
		        String.class);
		System.out.println("Devolucion 30:" + response.getBody());
		
		
		////// POSTS 
		
		RequestHolaMundo requestHolaMundo = new RequestHolaMundo();
		requestHolaMundo.setEstado("estado");
		requestHolaMundo.setNumero(999);
		requestHolaMundo.setPersona(persona);
		HttpEntity<RequestHolaMundo> request = new HttpEntity<RequestHolaMundo>(requestHolaMundo);
		ResponseHolaMundo responseHolaMundo = restTemplate.postForObject("http://localhost:8080/holamundo-servicio-rest/postKK", request, ResponseHolaMundo.class);
		
		
		
		System.out.println("Devolucion 40:" + responseHolaMundo.getCode() + " - " + responseHolaMundo.getMessage());
		
	}

}

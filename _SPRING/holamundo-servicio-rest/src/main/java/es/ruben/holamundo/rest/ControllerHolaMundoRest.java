package es.ruben.holamundo.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.ruben.holamundo.rest.dao.Persona;
import es.ruben.holamundo.rest.dao.RequestHolaMundo;
import es.ruben.holamundo.rest.dao.ResponseHolaMundo;

@RestController
public class ControllerHolaMundoRest {
	
	/**
	 * llamada a http://localhost:8080/holamundo-servicio-rest/version
	 * 
	 * @return
	 */
	@GetMapping (value = "/version")
	public String version() {
		System.out.println("entrando en /version");
		return "1.0";
	}
	
	// http://localhost:8080/holamundo-servicio-rest/getTrabajadores?nombre=superman
	@RequestMapping(value = "/getTrabajadores", method = RequestMethod.GET)
	public List<Persona> getTrabajadores(String nombre) {
		
		List<Persona> trabajadores = new ArrayList<Persona>();

		Persona pareja1= new Persona("leyre","paris",37,55.55);
		Persona persona1= new Persona("ruben","aguado perez",40,70.55,pareja1);
		
		Persona persona2= new Persona(nombre,"pico pato",50,80.55);
		
		trabajadores.add(persona1);
		trabajadores.add(persona2);
		
		return trabajadores;
	}
	
	@RequestMapping(value = "/getKK", method = RequestMethod.GET)
	public String getKK(@RequestBody RequestHolaMundo requestHolaMundo) {
		if (requestHolaMundo == null) {
			return "requestHolaMundo es null";
		}
		
		if (requestHolaMundo.estado == null) {
			return "requestHolaMundo.estado es null";
		}
		
		String s = "estado=" + requestHolaMundo.estado + " numero="+requestHolaMundo.getNumero() + " nombrePersona="+requestHolaMundo.getPersona().getNombre();
		return s;
				
	}
	
	//@RequestMapping(value = "/postKK" , method = RequestMethod.POST,headers="Accept=application/json")
	@RequestMapping(value = "/postKK" , method = RequestMethod.POST)
	public ResponseHolaMundo postKK (@RequestBody RequestHolaMundo requestHolaMundo) {
		ResponseHolaMundo responseHolaMundo = new ResponseHolaMundo();
		responseHolaMundo.setCode("0000");
		responseHolaMundo.setMessage("estado=" + requestHolaMundo.estado + " numero="+requestHolaMundo.getNumero() + " nombrePersona="+requestHolaMundo.getPersona().getNombre());
		//responseHolaMundo.setMessage("todo ok");
		
		return responseHolaMundo;
		
	}

}

package es.pruebas.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/prueba")
public class JerseryServer {
	
	public JerseryServer() {
		System.out.println("constructor ok");
	}
	
	@GET
	@Path("/hola")
	@Produces(MediaType.TEXT_PLAIN)
	public String hola() {
		System.out.println("entra en hola ");
		return "hola mundo";
	}

}

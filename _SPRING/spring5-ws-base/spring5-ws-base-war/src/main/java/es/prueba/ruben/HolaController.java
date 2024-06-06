package es.prueba.ruben;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HolaController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HolaController.class);

	private Usuario usuario = new Usuario(); 
	
	public HolaController() {
		usuario.setNombre("Rubén");
		usuario.setApellidos("Aguado Pérez");
	}

	@GetMapping("/hola")
	public String getHola() {
		LOGGER.debug("LLamando a WS mostar mensaje holamundo");
		return "hola mundo con spring 5";
	}
	
	@GetMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
//	@CrossOrigin(origins = {"*"}, maxAge = 86400, allowCredentials = "false")
	public Usuario getUsuario() {
		LOGGER.debug("LLamando a WS mostar usuario {}", usuario);
		return usuario;
	}
	
	@PutMapping(value="/usuario", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void setUsuario(@RequestBody Usuario usuario) {
		LOGGER.debug("Setteando usuario {} - {}", usuario.getNombre(), usuario.getApellidos());
		this.usuario = usuario;
	}
	
}

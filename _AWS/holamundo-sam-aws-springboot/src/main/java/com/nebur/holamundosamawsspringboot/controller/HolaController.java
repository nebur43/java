package com.nebur.holamundosamawsspringboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nebur.holamundosamawsspringboot.bean.Saludo;

@RestController
public class HolaController {

	private static Logger logger = LoggerFactory.getLogger(HolaController.class);
	
	private Saludo saludo;
	
	public HolaController() {
		saludo = new Saludo();
		saludo.setNombre("Ruben");
		saludo.setEdad(43);
		saludo.setIdioma("español");
		saludo.setSaludo("Hola Mundo!!!");
	}
	
	@GetMapping("/")
	public String getVersion() {
		logger.info("mostrando version");
		return "1.2";
	}
	
	@GetMapping("/saludo")
	public Saludo getSaludo() {
		logger.info("recuperando saludo...");
		return saludo;
	}
	
	@PostMapping("/saludo")
	public void setSaludo(@RequestBody Saludo saludo) {
		logger.info("seteando nuevo saludo. Nombre: {} - idioma: {} - saludo: {}", saludo.getNombre(), saludo.getIdioma(), saludo.getSaludo());
		this.saludo = saludo;
	}
	
	@PostMapping("/stop")
	public void stop() {
		logger.info("parando servicio");
		System.exit(0);
	}
}

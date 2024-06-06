package com.gfi.mongodb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gfi.mongodb.model.Programador;
import com.gfi.mongodb.repository.ProgramadorRepository;
import com.gfi.mongodb.service.ProgramadorService;

@RestController
@RequestMapping("/")
public class ProgramadorController {

	@Resource
	private ProgramadorRepository repository;

	@Resource
	private ProgramadorService service;

	/**
	 * Peticion GET http://localhost:9008/mongo
	 */
	@GetMapping
	public List<Programador> getAll() {
		return repository.findAll();
		// return service.findAll();
	}

	/**
	 * Peticion POST http://localhost:9008/mongo/create
	 * 
	 * { "nombre": "Julio Arranz", "proyecto": { "cliente": "Mapfre", "tecnologia":
	 * "Spring Boot - Maven" } }
	 */
	@PostMapping("/create")
	public Programador create(@RequestBody Programador programador) {
		System.out.println("programador " + programador);
		return repository.save(programador);
	}

	/**
	 * Peticion GET http://localhost:9008/mongo/{id}
	 */
	@GetMapping(value = "/{idProgramador}")
	public Object getProgramador(@PathVariable String idProgramador) {
		Programador programador = repository.findById(idProgramador).get();
		if (programador != null) {
			return service.findProgramadorById(idProgramador);
		}
		return "Programador no encontrado " + idProgramador;
	}

	/**
	 * Peticion GET http://localhost:9008/mongo/proyecto/{idProgramador}
	 */
	@GetMapping(value = "/proyecto/{idProgramador}")
	public Object getProyectoProgramador(@PathVariable String idProgramador) {
		Programador programador = repository.findById(idProgramador).get();
		if (programador != null) {
			return service.getProjectByProgrammer(idProgramador);
		}
		return "Programador no encontrado " + idProgramador;
	}

	/**
	 * Peticion GET http://localhost:9008/mongo/proyecto/{idProgramador}/{campo}
	 */
	@GetMapping(value = "/proyecto/{idProgramador}/{campo}")
	public Object getCampoProyectoProgramador(@PathVariable String idProgramador, @PathVariable String campo) {
		return service.getItemProjectByProgrammer(idProgramador, campo);
	}

	/**
	 * Peticion GET
	 * http://localhost:9008/mongo/proyecto/{idProgramador}/{campo}/{valor}
	 */
	@GetMapping(value = "/proyecto/{idProgramador}/{campo}/{valor}")
	public Object updateCampoProyectoProgramador(@PathVariable String idProgramador, @PathVariable String campo,
			@PathVariable String valor) {
		/*
		Programador programador = repository.findById(idProgramador).get();
		if (programador != null) {
			programador.getProyecto().put(campo, valor);
			repository.save(programador);
			return "Datos proyecto actualizados";
		}
		return "Programador no encontrado";
		*/
		
		return service.updateItemProjectByProgrammer(idProgramador, campo, valor);
	}
}

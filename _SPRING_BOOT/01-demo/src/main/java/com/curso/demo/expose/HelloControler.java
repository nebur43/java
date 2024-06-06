package com.curso.demo.expose;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.demo.model.Saludo;
import com.curso.demo.service.DemoService;

@RestController
public class HelloControler {
	
	@Autowired
	private DemoService demoService;
	
	@GetMapping("/")
	public String hello() {
		return "hola mundillo!";
	}
	
	@GetMapping("/saludo")
	public Saludo saludo() {
		return this.demoService.saludo();
	}
}

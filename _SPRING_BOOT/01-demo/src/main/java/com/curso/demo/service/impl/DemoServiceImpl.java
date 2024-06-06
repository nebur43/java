package com.curso.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.demo.model.Saludo;
import com.curso.demo.repository.SaludoRepository;
import com.curso.demo.service.DemoService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DemoServiceImpl implements DemoService {

	@Autowired
	private SaludoRepository saludoRepository;
	
	@Override
	public Saludo saludo() {
		log.info("LLAMANDO AL SERVICIO DEMO!");
		return saludoRepository.findById(1L).get();
	}
	
}

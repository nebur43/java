package com.gfi.mongodb.service;

import java.util.List;

import com.gfi.mongodb.model.Programador;

public interface ProgramadorService {

	List<Programador> findAll();
	
	Programador findProgramadorById(String id);
	
	Programador create(Programador programador);
	
	Object getProjectByProgrammer(String id);
	
	String getItemProjectByProgrammer(String id, String key);
	
	String updateItemProjectByProgrammer(String id, String key, String value);
	
}

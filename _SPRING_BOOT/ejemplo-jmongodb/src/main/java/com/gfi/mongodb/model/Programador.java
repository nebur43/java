package com.gfi.mongodb.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento MondoDG (Equivalente a entidad)
 */
@Document
public class Programador {

	/**
	 * Campo clave del documento
	 */
	@Id
	private String id;
	
	private String nombre;
	
	private Date alta = new Date();
	
	private Map<String, String> proyecto = new HashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getAlta() {
		return alta;
	}

	public void setAlta(Date alta) {
		this.alta = alta;
	}

	public Map<String, String> getProyecto() {
		return proyecto;
	}

	public void setProyecto(Map<String, String> proyecto) {
		this.proyecto = proyecto;
	}
	
}

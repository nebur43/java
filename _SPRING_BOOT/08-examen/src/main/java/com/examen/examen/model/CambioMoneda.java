package com.examen.examen.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CambioMoneda {

	@Id
	private Long id;
	private String monedaOrigen;
	private String monedaDestino;
	private float cambio;
	
}

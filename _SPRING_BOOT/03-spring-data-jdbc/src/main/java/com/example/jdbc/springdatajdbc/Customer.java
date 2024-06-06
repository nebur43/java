package com.example.jdbc.springdatajdbc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Customer {

	private Long id;
	
	private String nombre;
	
	private String apellido;
	
	public Customer() {
	}
	
	public Customer(String nombre, String apellido) {
		this.nombre = nombre;
		this.apellido = apellido;
	}
	
	@Override
	public String toString() {
		return String.format("Customer[id=%d], nombre=%s, apellidos=%s", id,nombre,apellido);
	}
}

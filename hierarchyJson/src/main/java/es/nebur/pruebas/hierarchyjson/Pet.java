package es.nebur.pruebas.hierarchyjson;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class Pet {

	private String name;
	private Owner owner;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
//	@JsonBackReference
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	
	
	
}

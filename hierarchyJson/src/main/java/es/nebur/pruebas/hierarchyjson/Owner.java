package es.nebur.pruebas.hierarchyjson;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Owner {

	private String name;
	private String surmane;
	private Pet pet;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurmane() {
		return surmane;
	}
	public void setSurmane(String surmane) {
		this.surmane = surmane;
	}
	public Pet getPet() {
		return pet;
	}
	
//	@JsonManagedReference()
	public void setPet(Pet pet) {
		this.pet = pet;
	}
	
	
	
}

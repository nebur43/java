package es.nebur.pruebas.hierarchyjson;

import java.util.ArrayList;

public class BoxPet {

	private String boxName;
	private int countPets;
	private ArrayList<Pet> pets;
	
	public String getBoxName() {
		return boxName;
	}
	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}
	public int getCountPets() {
		return countPets;
	}
	public void setCountPets(int countPets) {
		this.countPets = countPets;
	}
	public ArrayList<Pet> getPets() {
		return pets;
	}
	public void setPets(ArrayList<Pet> pets) {
		this.pets = pets;
	}
	
	
	
}

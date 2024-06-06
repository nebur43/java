package es.ruben.holamundo.rest.dao;

public class Persona {
	
	public String nombre;
	public String apellidos;
	public int edad;
	public double peso;
	
	public Persona pareja;
	
	public Persona() {
		
	}
	
	public Persona(String nombre, String apellidos, int edad, double peso) {
		this.setNombre(nombre);
		this.setApellidos(apellidos);
		this.setEdad(edad);
		this.setPeso(peso);
	}
	
	public Persona(String nombre, String apellidos, int edad, double peso, Persona pareja) {
		this.setNombre(nombre);
		this.setApellidos(apellidos);
		this.setEdad(edad);
		this.setPeso(peso);
		
		this.setPareja(pareja);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public Persona getPareja() {
		return pareja;
	}

	public void setPareja(Persona pareja) {
		this.pareja = pareja;
	}
	
	

}

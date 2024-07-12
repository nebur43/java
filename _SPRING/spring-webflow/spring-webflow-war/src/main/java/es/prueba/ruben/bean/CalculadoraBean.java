package es.prueba.ruben.bean;

import java.io.Serializable;

public class CalculadoraBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -12766139059414999L;
	
	private double a;
	private double b;
	public double getA() {
		return a;
	}
	public void setA(double a) {
		this.a = a;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}
	
}

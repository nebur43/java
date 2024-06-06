package es.iecisa.jfaces;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * pagina principal es admimistrado por Spring, y utiliza jsf 
 * 
 * @author 67760769
 *
 */
public class PaginaPrincipal {
	
	private static int count = 0;
	
	private String saludo = "Mostrando PaginaPrincipal con servida con spring session scope :"+count;
	private String nombre = "Rub�n";
	
	
	public PaginaPrincipal() {
		count++;
	}
	
	// hace un foward
	public String avanzar() {
		return "OK";
	}
	
	// un redirect (cambia la url)
	public String avanzarRedirect() {
		return "adios.html?faces-redirect=true";
	}
	
	// un redirect2 (cambia la url)
	public String avanzarRedirect2() throws IOException {
		FacesContext fContext = FacesContext.getCurrentInstance();
		ExternalContext extContext = fContext.getExternalContext();
		extContext.redirect(extContext.getRequestContextPath() + "/adios.xhtml");
		return "KKK";
	}
	
	// hace un foward
		public String avanzarMal() {
			return "KKK";
		}
	
	
	public String getSaludo() {
		return saludo;
	}
	public void setSaludo(String saludo) {
		this.saludo = saludo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}

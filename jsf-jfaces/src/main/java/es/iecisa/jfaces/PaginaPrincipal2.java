package es.iecisa.jfaces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * pagina principal es admimistrado por JSF, y jsf inyecta beans de spring 
 * 
 * @author 67760769
 *
 */
@ManagedBean
@SessionScoped
public class PaginaPrincipal2 {
	
	private static int count = 0;
	private int numero=0;
	
	@ManagedProperty(value = "#{paginaPrincialService}")
	private PaginaPrincialService paginaPrincialService;
	
	private String saludo = "Mostrando PaginaPrincipal con servida con jfaces @ManagedBean y@SessionScoped ";
	private String nombre = "Rub�n";
	
	public PaginaPrincipal2() {
		count++;
		numero = count;
	}
	
	public String avanzar() {
		return "OK";
	}
	
	public String getSaludo() {
		return paginaPrincialService.concatenar(saludo, numero);
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

	public PaginaPrincialService getPaginaPrincialService() {
		return paginaPrincialService;
	}

	public void setPaginaPrincialService(PaginaPrincialService paginaPrincialService) {
		this.paginaPrincialService = paginaPrincialService;
	}
	
	
	

}

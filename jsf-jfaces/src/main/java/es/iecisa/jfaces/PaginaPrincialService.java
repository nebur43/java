package es.iecisa.jfaces;

import org.springframework.stereotype.Service;

@Service
public class PaginaPrincialService {

	public PaginaPrincialService () {
		System.out.println(" **** Construyendo PaginaPrincialService **** ");
	}
	
	public String concatenar(String saludo , int numero) {
		return saludo + " " + numero;
	}
	
	
	
}

package com.examen.examen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examen.examen.controller.bean.CambioMonedaResponse;
import com.examen.examen.service.CambioMonedaBean;
import com.examen.examen.service.CambioMonedaException;
import com.examen.examen.service.ICambioMonedaService;

@RestController
public class CambioMonedaController {

	@Autowired
	private ICambioMonedaService cambioMonedaService;
	
	@GetMapping("calcularCambio/{monedaOrigen}/{monedaDestino}/{monto}")
	public CambioMonedaResponse calcularCambio(@PathVariable String monedaOrigen,@PathVariable String monedaDestino, @PathVariable float monto) throws CambioMonedaException {
		CambioMonedaBean cambioMonedaBean = this.cambioMonedaService.calcularCambio(monedaOrigen, monedaDestino, monto);
		CambioMonedaResponse response = new CambioMonedaResponse();
		response.setMonedaOrigen(monedaOrigen);
		response.setMonedaDestino(monedaDestino);
		response.setMonto(monto);
		response.setMontoTipoCambio(cambioMonedaBean.getMontoCambio());
		response.setTipoCambio(cambioMonedaBean.getTipoCambio());
		return response;
	}
	
	@PostMapping("updateCambio/{monedaOrigen}/{monedaDestino}/{tipoCambio}")
	public String updateTipoCambio(@PathVariable String monedaOrigen,@PathVariable String monedaDestino, @PathVariable float tipoCambio) throws CambioMonedaException {
		this.cambioMonedaService.updateTipoCambio(monedaOrigen, monedaDestino, tipoCambio);
		return "OK";
	}
	
	@ExceptionHandler(CambioMonedaException.class)
	public ResponseEntity<String> handleExeption(CambioMonedaException ex) {
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
}

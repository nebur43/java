package com.examen.examen.controller.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CambioMonedaResponse {

	private float monto;
	private float montoTipoCambio;
	private String monedaOrigen;
	private String monedaDestino;
	private float tipoCambio;
}

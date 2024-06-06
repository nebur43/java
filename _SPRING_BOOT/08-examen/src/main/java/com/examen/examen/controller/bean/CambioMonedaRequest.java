package com.examen.examen.controller.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CambioMonedaRequest {

	private float monto;
	private String monedaOrigen;
	private String monedaDestino;
}

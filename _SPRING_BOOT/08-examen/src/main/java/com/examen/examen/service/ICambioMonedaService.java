package com.examen.examen.service;

public interface ICambioMonedaService {

	CambioMonedaBean calcularCambio(String monedaOrigen, String monedaDestino, float monto) throws CambioMonedaException;

	void updateTipoCambio(String monedaOrigen, String monedaDestino, float tipoCambio) throws CambioMonedaException;

}
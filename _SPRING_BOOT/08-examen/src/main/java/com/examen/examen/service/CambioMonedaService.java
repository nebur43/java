package com.examen.examen.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examen.examen.model.CambioMoneda;
import com.examen.examen.repository.CambioMonedaRepository;

@Service
public class CambioMonedaService implements ICambioMonedaService {

	@Autowired
	private CambioMonedaRepository repository;
	
	@Override
	public CambioMonedaBean calcularCambio(String monedaOrigen, String monedaDestino, float monto) throws CambioMonedaException {
		Optional<CambioMoneda> op = this.repository.findByMonedaOrigenAndMonedaDestino(monedaOrigen, monedaDestino);
		if (op.isPresent()) {
			CambioMoneda cambioMoneda = op.get();
			return new CambioMonedaBean(cambioMoneda.getCambio() * monto, cambioMoneda.getCambio());
		}
		throw new CambioMonedaException("Tipo cambio no encontrado");
	}
	
	@Override
	public void updateTipoCambio(String monedaOrigen, String monedaDestino, float tipoCambio) throws CambioMonedaException {
		Optional<CambioMoneda> op = this.repository.findByMonedaOrigenAndMonedaDestino(monedaOrigen, monedaDestino);
		if (op.isPresent()) {
			op.get().setCambio(tipoCambio);
			this.repository.save(op.get());
		} else {
			throw new CambioMonedaException("Tipo cambio no encontrado");
		}
	}
}

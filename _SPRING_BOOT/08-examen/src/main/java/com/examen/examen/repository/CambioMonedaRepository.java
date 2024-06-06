package com.examen.examen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examen.examen.model.CambioMoneda;

public interface CambioMonedaRepository extends JpaRepository<CambioMoneda, Long> {
	
	Optional<CambioMoneda> findByMonedaOrigenAndMonedaDestino(String monedaOrigen, String monedaDestino);
}

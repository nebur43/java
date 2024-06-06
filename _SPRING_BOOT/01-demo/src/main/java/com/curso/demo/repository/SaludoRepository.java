package com.curso.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.demo.model.Saludo;

public interface SaludoRepository extends JpaRepository<Saludo, Long>{

}

package com.gfi.jpa.relations.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gfi.jpa.relations.model.Categoria;
import com.gfi.jpa.relations.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

	List<Producto> findByCategoria(Categoria categoria);
	
}

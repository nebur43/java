package com.example.datarest.springdatarest;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "personas" , path = "personas")
//public interface PersonaRepository extends PagingAndSortingRepository<Persona, Long>{
public interface PersonaRepository extends CrudRepository<Persona, Long>{

	List<Persona> findByApellido(@Param("apellido") String apellido);
	
}

package com.gfi.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gfi.mongodb.model.Programador;

@Repository
public interface ProgramadorRepository extends MongoRepository<Programador, String> {
	
}

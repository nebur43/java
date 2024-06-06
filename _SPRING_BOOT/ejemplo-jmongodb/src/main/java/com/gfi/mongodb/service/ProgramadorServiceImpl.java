package com.gfi.mongodb.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gfi.mongodb.model.Programador;

@Service
public class ProgramadorServiceImpl implements ProgramadorService {

	/**
	 * Inyectar Plantilla MongoDB. Control más fino sobre los docuemntos y
	 * colecciones que el repositiorio
	 */
	@Resource
	private MongoTemplate template;

	@Override
	public List<Programador> findAll() {
		return template.findAll(Programador.class);
	}

	@Override
	public Programador findProgramadorById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return template.findOne(query, Programador.class);
	}

	@Override
	public Programador create(Programador programador) {
		template.save(programador);

		return programador;
	}

	@Override
	public Object getProjectByProgrammer(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Programador programador = template.findOne(query, Programador.class);
		return programador != null ? programador.getProyecto() : "No encontrado";
	}

	@Override
	public String getItemProjectByProgrammer(String id, String key) {
		Query query = new Query();
		query.fields().include("proyecto");
		query.addCriteria(Criteria.where("id").is(id).andOperator(Criteria.where("proyecto." + key).exists(true)));
		Programador programador = template.findOne(query, Programador.class);
		return programador != null ? programador.getProyecto().get(key) : "No encontrado";
	}

	@Override
	public String updateItemProjectByProgrammer(String id, String key, String value) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Programador programador = template.findOne(query, Programador.class);
		if (programador != null) {
			programador.getProyecto().put(key, value);
			template.save(programador);
			return "Proyecto actualizado";
		}
		return "Programador no encontrado";
	}

}

package com.example.elasticsearch.helloworld.repo;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.elasticsearch.helloworld.dao.User;

public interface UserRepo extends ElasticsearchRepository<User, String>{

	List<User> findAllByName(String name);
	
	@Query("{\"match\":{\"name\":\"?0\"}}")
	List<User> findAllByNameUsingAnotations(String name);
	
}

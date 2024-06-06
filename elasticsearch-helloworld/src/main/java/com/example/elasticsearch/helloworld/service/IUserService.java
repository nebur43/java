package com.example.elasticsearch.helloworld.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.example.elasticsearch.helloworld.dao.User;

public interface IUserService {

	User createUser(User user);

	void deleteUser(User user);

	Optional<User> getUser(String id);

	List<User> getAll();

	List<User> getByName(String name);

	List<User> getByNameQuery(String name);

	List<User> getUserByNameSpecial(String name);

	void createUserRestHighLevelClient(User user) throws IOException;

}
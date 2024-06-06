package com.example.elasticsearch.helloworld.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.elasticsearch.helloworld.dao.User;
import com.example.elasticsearch.helloworld.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

	private final UserRepo userRepo;
	
	private final ElasticsearchRestTemplate elasticsearchRestTemplate;
	
	private final RestHighLevelClient restHighLevelClient;
	
	@Override
	public User createUser(User user) {
		return this.userRepo.save(user);
	}
	
	@Override
	public void deleteUser(User user) {
		this.userRepo.delete(user);
	}
	
	@Override
	public Optional<User> getUser(String id) {
		return this.userRepo.findById(id);
	}
	
	@Override
	public List<User> getAll() {
		ArrayList<User> usuarios = new ArrayList<>();
		this.userRepo.findAll().forEach(usuarios::add);
		return usuarios;
	}
	
	@Override
	public List<User> getByName(String name) {
		ArrayList<User> usuarios = new ArrayList<>();
		this.userRepo.findAllByName(name).forEach(usuarios::add);
		return usuarios;
	}
	
	@Override
	public List<User> getByNameQuery(String name) {
		ArrayList<User> usuarios = new ArrayList<>();
		this.userRepo.findAllByNameUsingAnotations(name).forEach(usuarios::add);
		return usuarios;
	}
	
	@Override
	public List<User> getUserByNameSpecial(String name) {
	    Query query = new NativeSearchQueryBuilder()
	        .withQuery(QueryBuilders.matchQuery("name", name))
	        .build();

	    SearchHits<User> searchHits = elasticsearchRestTemplate.search(query, User.class);
	    return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
	}
	
//	public Map<String, Long> aggregateTerm(String term) {
//	    Query query = new NativeSearchQueryBuilder()
//	        .addAggregation(new TermsAggregationBuilder(term).field(term).size(10))
//	        .build();
//
//	    SearchHits<User> searchHits = elasticsearchRestTemplate.search(query, User.class);
//
//	    Map<String, Long> result = new HashMap<>();
//	    searchHits.getAggregations().asList().forEach(aggregation -> {
//	      ((Terms) aggregation).getBuckets()
//	          .forEach(bucket -> result.put(bucket.getKeyAsString(), bucket.getDocCount()));
//	    });
//
//	    return result;
//	  }
	
	@Override
	@Deprecated
	// no funciona 
	public void createUserRestHighLevelClient(User user) throws IOException {
		IndexRequest indexRequest = new IndexRequest("usuarios2");
	    indexRequest.id(user.getId());
	    indexRequest.source(user);
	    
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        if (indexResponse.status() != RestStatus.ACCEPTED) {
        	throw new IOException("Wrong status: " + indexResponse.status());
        }

	        
	}
	
}

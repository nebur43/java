package com.example.elasticsearch.helloworld.dao;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "usuarios", createIndex = true)
public class User {

	@Id
	private String id;

	@Field(type = FieldType.Text, name = "name")
	private String name;
	
	@Field(type = FieldType.Text, name = "surname")
	private String surname;

	@Field(type = FieldType.Date, name = "birth")
	private Date birth;

	@Field(type = FieldType.Integer, name = "child")
	private Integer child;

	@Field(type = FieldType.Keyword, name = "sex")
	private String sex;

	public enum Sex {
		MALE, FEMALE, OTHER;
	}
}

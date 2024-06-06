package com.nebur.comment.bean;

import java.io.Serializable;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Foro implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7303317953269469675L;
	
	private long id;
	private long dateForo;
	private String asunto;
	private String usuario;
	
	@DynamoDbPartitionKey
	public long getId() {
		return id;
	}
	@DynamoDbSortKey
	public long getDateForo() {
		return dateForo;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public void setDateForo(long dateForo) {
		this.dateForo = dateForo;
	}
	
	
	
}

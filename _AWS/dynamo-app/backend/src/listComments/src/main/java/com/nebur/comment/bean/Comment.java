package com.nebur.comment.bean;

import java.io.Serializable;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Comment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7303317953269469675L;
	
	
	private long dateForo;
	private long dateComentario;
	private String comentario;
	private String usuario;
	
	@DynamoDbPartitionKey
	public long getDateForo() {
		return dateForo;
	}
	public void setDateForo(long dateForo) {
		this.dateForo = dateForo;
	}
	
	@DynamoDbSortKey
	public long getDateComentario() {
		return dateComentario;
	}
	public void setDateComentario(long dateComentario) {
		this.dateComentario = dateComentario;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}

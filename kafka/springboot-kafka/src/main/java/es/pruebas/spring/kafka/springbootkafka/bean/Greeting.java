package es.pruebas.spring.kafka.springbootkafka.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Greeting {

	private String msg;
    private String name;
    
    public Greeting(@JsonProperty("msg") String msg, @JsonProperty("name") String name) {
    	this.msg = msg;
    	this.name = name;
	}
    
	public String getMsg() {
		return msg;
	}
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Greeting["+name+"]:"+msg;
	}
	
}

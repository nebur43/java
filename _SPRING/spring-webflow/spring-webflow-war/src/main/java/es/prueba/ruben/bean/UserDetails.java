package es.prueba.ruben.bean;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDetails implements Serializable{
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetails.class);
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -702645489766140158L;
	private String name;
    private String email;

    // Getters y setters
    public String getName() {
    	logger.debug("getting name {}", name );
        return name;
    }

    public void setName(String name) {
    	logger.debug("setting name {}", name );
        this.name = name;
    }

    public String getEmail() {
    	logger.debug("getting email {}", email );
        return email;
    }

    public void setEmail(String email) {
    	logger.debug("setting email {}", email );
        this.email = email;
    }
}
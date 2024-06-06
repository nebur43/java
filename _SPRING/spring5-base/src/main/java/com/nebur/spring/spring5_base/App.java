package com.nebur.spring.spring5_base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
	
	private static final Logger log = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args ) {
    	ApplicationContext contenedor = new ClassPathXmlApplicationContext("applicationContext.xml");
    	Bean1 bean1 = contenedor.getBean("bean1",Bean1.class);
    	log.debug("mensaje: {}", bean1.getMensaje());
    }
}

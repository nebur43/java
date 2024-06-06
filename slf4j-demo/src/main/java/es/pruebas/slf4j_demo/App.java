package es.pruebas.slf4j_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
	 public static void main( String[] args )
	    {
	        Logger logger = LoggerFactory.getLogger(App.class);
	        logger.info("This is how you configure Log4J with SLF4J");
	    }
}

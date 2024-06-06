package es.ruben.pruebas.holamundo;

import org.slf4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

public class HolaMundo {
	
//	private static Logger logger = LogManager.getLogger(HolaMundo.class);
	private static Logger logger = LoggerFactory.getLogger(HolaMundo.class);
	
	public HolaMundo() {
		logger.debug("Creado objeto HolaMundo");
	}
	
	public void imprimirHolaMundo() {
		logger.debug("Hola Mundo!!!! desde la clase HolaMundo.java");
	}
	

}

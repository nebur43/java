package es.prueba.ruben;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Calculadora {
	
	private static final Logger logger = LoggerFactory.getLogger(Calculadora.class);

	public double suma(double a, double b) {
		logger.debug("sumando {} y {}", a , b);
		return a + b;
	}
	
}

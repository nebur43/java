package es.prueba.ruben;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MiTest.class);
	
	@Test
	public void test1() {
		LOGGER.debug("EJECUTANDO TEST 1");
		assertTrue(true);
	}
	
}

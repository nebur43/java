package com.nebur.openai_client.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class JsonUtil {
	
	private static final Logger LOGGER = LogManager.getLogger(JsonUtil.class);
	
	private static ObjectMapper mapper = new ObjectMapper().registerModule(new Jdk8Module());

	public static String parse(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			LOGGER.error("Error serializing object to JSON", e);
			return null;
		}
    }
	
	public static String prettyPrint(Object obj) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (Exception e) {
			LOGGER.error("Error pretty printing object to JSON", e);
			return null;
		}
	}
	
}

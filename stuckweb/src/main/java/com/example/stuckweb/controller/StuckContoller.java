package com.example.stuckweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StuckContoller {

	private static Logger log = LoggerFactory.getLogger(StuckContoller.class);
	
	@GetMapping("/stuck")
	public String stuck() throws InterruptedException {
		log.info("INICIO GET");
		Thread.sleep(700000);
		log.info("FIN GET");
		return "FIN GET";
	}
	
	@PostMapping("/stuck")
	public String stuckPost(@RequestBody String r) throws InterruptedException {
		log.info("INICIO POST {}", r);
		Thread.sleep(700000);
		log.info("FIN POST");
		return "FIN POST";
	}
	
}

package com.example.webrest;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@Controller()
public class GreetingController {

	private static final String TEMPLATE="Hola, %s!";
	
	private final AtomicLong counter = new AtomicLong();
	
	@GetMapping("greeting")
	public Greeting greeting(@RequestParam(defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(),String.format(TEMPLATE, name) );
	}
	
}

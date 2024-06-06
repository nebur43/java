package com.example.demo;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.dto.Customer;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean	public CommandLineRunner prueba(CustomerRepository repository) {
		return args -> {
		
			repository.save(new Customer("ruben","aguado"));
			repository.save(new Customer("adrian","aguado"));
			repository.save(new Customer("pepe","perez"));
			
			log.info("*************** usando findAll *************");
			for (Customer c:repository.findAll()) {
				log.info(c);
			}
			
			log.info("*************** usando findAll *************");
			Optional<Customer> customer = repository.findById(2L);
			log.info(customer);
			
			log.info("*************** usando findByApellido *************");
			List<Customer> cApellidos = repository.findByApellido("aguado");
			for (Customer c:cApellidos) {
				log.info(c);
			}
			
			log.info("*************** usando findByApellidoAndId *************");
			Customer c2 = repository.findByApellidoAndId("aguado",2L);
			log.info(c2);
			
		};
	}
	
}

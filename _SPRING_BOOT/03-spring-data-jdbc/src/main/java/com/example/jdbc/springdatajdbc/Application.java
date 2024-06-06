package com.example.jdbc.springdatajdbc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner{
	
	private static final Logger log= LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void run(String... args) throws Exception {
		
		log.info("Probando jdbc");
		jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE customers (id SERIAL, nombre VARCHAR(200), apellido VARCHAR(200))");
		
		List<Object[]> nombres = Arrays.asList("Ruben Aguado", "Manuel davila" , "Alejandra Perez").stream()
				.map(nombre -> nombre.split(" "))
				.collect(Collectors.toList());
		
		nombres.forEach(n -> {
				log.info(String.format("Insertando Nombre = %s    y       apellidos = %s ", n[0],n[1]));
			});
		
		jdbcTemplate.batchUpdate("INSERT INTO customers (nombre, apellido) VALUES (?,?)",nombres);
		
		jdbcTemplate.query("SELECT id, nombre , apellido from customers where nombre = ?", new Object[] {"Alejandra"},
				(r,num) -> new Customer(r.getLong("id"),r.getString("nombre"),r.getString("apellido")))
			.forEach(customer -> log.info("Customer= " + customer));
	}
	
	
}

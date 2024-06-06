package com.gfi.jpa.relations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gfi.jpa.relations.model.Categoria;
import com.gfi.jpa.relations.model.Producto;
import com.gfi.jpa.relations.repository.CategoriaRepository;
import com.gfi.jpa.relations.repository.ProductoRepository;

@SpringBootApplication
public class EjemploJpaRelacionesApplication implements CommandLineRunner {

	@Resource
	private CategoriaRepository categoriaRepository;

	@Resource
	private ProductoRepository productoRepository;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EjemploJpaRelacionesApplication.class);

		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria categoria1 = new Categoria("Bicicletas", "BIK");
		Categoria categoria2 = new Categoria("Accesorios", "HAN");

		categoriaRepository.save(categoria1);
		categoriaRepository.save(categoria2);

		Producto producto1 = new Producto("Bicicleta Carretera", 1999.99, categoria1);
		Producto producto2 = new Producto("Bicicleta Montaña", 999.99, categoria1);
		Producto producto3 = new Producto("Bicicleta Paseo", 199.99, categoria1);

		productoRepository.save(producto1);
		productoRepository.save(producto2);
		productoRepository.save(producto3);

		listCategories();
/*
		Categoria categoriaFinded = categoriaRepository.findByRef("HAN");

		categoriaFinded.getProductos().add(new Producto("Llanta alumninio 17\"", 99.99, categoriaFinded));

		categoriaRepository.save(categoriaFinded);

		listCategories();

		List<Producto> productos = productoRepository.findAll();

		productos.forEach(System.out::println);

		System.out.println();

		productos = productoRepository.findByCategoria(categoria1);

		productos.forEach(System.out::println);
		
		System.out.println();

		Producto biciRoad = productoRepository.findById(1).get();

		Categoria catBiciRoad = biciRoad.getCategoria();
		
		System.out.println(catBiciRoad);
		
		categoriaRepository.delete(categoria2);
		
		listCategories();
		
		productos = productoRepository.findAll();

		productos.forEach(System.out::println);

		System.out.println();
		
		try {
			
			Producto producto5 = new Producto("Bicicleta Paseo", 199.99, null);
			
			productoRepository.save(producto5);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

	private void listCategories() {
		System.out.println("Datos Actuales");

		List<Categoria> categorias = categoriaRepository.findAll();

		categorias.forEach(System.out::println);

		System.out.println();
	}

}

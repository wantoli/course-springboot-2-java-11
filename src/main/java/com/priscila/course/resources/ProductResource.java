package com.priscila.course.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.priscila.course.entities.Product;
import com.priscila.course.entities.Saida;
import com.priscila.course.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	
	private final Logger log = LoggerFactory.getLogger(ProductResource.class);
	
	@Autowired
	private ProductService service;
	
	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		
		log.info("Processando lista de produtos");
		List<Product> list = service.findAll();
		
		return ResponseEntity.ok().body(list);
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		
		log.info("Processando lista de produto por id");
		Product obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
		
	}

	@GetMapping(value = "/description/{name}")
	public ResponseEntity<List<Product>> findByNameLike(@PathVariable String name) {
		
		List<Product> obj = service.findByNameLike(name);
		
		for (Product x : obj) {
			
			Saida s = new Saida();
			s.setDescription(x.getDescription());
		}
		
		return ResponseEntity.ok().body(obj);
		
	}
	
	@PutMapping(value= "/{id}/Categories")
	public ResponseEntity<Product> update (@PathVariable Long id, @RequestBody Product obj, 
			@RequestParam("optional") String action) {
		
		log.info("Processando as categorias a partir de um id");
		obj = service.updateCategory(id, obj, action);
		return ResponseEntity.ok().body(obj);
		
	}
	
}

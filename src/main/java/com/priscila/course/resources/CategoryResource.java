package com.priscila.course.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.priscila.course.entities.Category;
import com.priscila.course.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	private final Logger log = LoggerFactory.getLogger(CategoryResource.class);
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		
		log.info("Processando lista de categorias");
		List<Category> list = service.findAll();
		
		return ResponseEntity.ok().body(list);
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		
		log.info("Processando categoria por id ");
		Category obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
		
	}

}

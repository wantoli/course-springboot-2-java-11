package com.priscila.course.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.priscila.course.entities.Category;
import com.priscila.course.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	private final Logger log = LoggerFactory.getLogger(CategoryService.class);
	
	@Autowired
	private CategoryRepository repository;
	
	public List<Category> findAll() {
		
		log.info("Buscando todos os usuarios");
		return repository.findAll();
	}
	
	public Category findById(Long id) {
		
		log.info("Buscando todas as categorias");
		Optional<Category> obj = repository.findById(id);
		return obj.get();
	}

}

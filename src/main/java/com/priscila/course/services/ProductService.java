package com.priscila.course.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.priscila.course.entities.Category;
import com.priscila.course.entities.Product;
import com.priscila.course.repositories.ProductRepository;
import com.priscila.course.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	public List<Product> findAll() {
		
		return repository.findAll();
	}
	
	public Product findById(Long id) {
		
		Optional<Product> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id, " Produto nao encontrado "));
	}

	public List<Product> findByNameLike(String name){
		
		return repository.findByNameLike("%" + name + "%");
		
	}
	
	public Product findFirst1ByName(String name){
		
		return repository.findFirst1ByName(name);
		
	}
	
	public Product updateCategory(Long id, Product obj, String action) {
		try {
			Product entity = repository.getOne(id);
			updateData(entity, obj, action);
			return repository.save(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id, e.getMessage());
		}
	}
	
	private void updateData(Product original, Product updated, String action) {
		
		for (Category obj : updated.getCategories()) {
			if (action == "add") {
				original.addCategory(obj);
			}
			if (action == "remove") {
				original.removeCategory(obj);
			}
		}
	}
}

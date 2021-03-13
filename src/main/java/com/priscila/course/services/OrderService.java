package com.priscila.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.priscila.course.entities.Order;
import com.priscila.course.repositories.OrderItemRepository;
import com.priscila.course.repositories.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private OrderItemRepository repositoryItem;
	
	public List<Order> findAll() {
		
		return repository.findAll();
	}
	
	public Order findById(Long id) {
		
		Optional<Order> obj = repository.findById(id);
		return obj.get();
	}

	public Double sumPrice(Long id) {
		return repositoryItem.selectTotal(id);
	}
}

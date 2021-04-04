package com.priscila.course.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.priscila.course.entities.Order;
import com.priscila.course.repositories.OrderItemRepository;
import com.priscila.course.repositories.OrderRepository;

@Service
public class OrderService {
	
	private final Logger log = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private OrderItemRepository repositoryItem;
	
	public List<Order> findAll() {
		
		log.info("Buscando todos os itens de uma ordem");
		return repository.findAll();
	}
	
	public Order findById(Long id) {
		
		log.info("Buscando um item especifico de uma ordem");
		Optional<Order> obj = repository.findById(id);
		return obj.get();
	}

	public Double sumPrice(Long id) {
		return repositoryItem.selectTotal(id);
	}
}

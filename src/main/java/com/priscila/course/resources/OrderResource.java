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

import com.priscila.course.entities.Order;
import com.priscila.course.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {
	
	private final Logger log = LoggerFactory.getLogger(OrderResource.class);
	
	@Autowired
	private OrderService service;
	
	@GetMapping
	public ResponseEntity<List<Order>> findAll() {
		
		log.info("Processando lista de ordens");
		List<Order> list = service.findAll();
		
		return ResponseEntity.ok().body(list);
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id) {
		
		log.info("Processando lista de ordem por id");
		Order obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
		
	}

	@GetMapping(value = "/{id}/total")
	public ResponseEntity<Double> sumPrice(@PathVariable Long id) {
		
		Double x = service.sumPrice(id);
		return ResponseEntity.ok().body(x);
		
	}
}

package com.priscila.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.priscila.course.entities.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
	@Query(value = "SELECT SUM(TOTAL) FROM TB_ORDER_ITEM WHERE ORDER_ID = :id", nativeQuery = true)
	Double selectTotal(@Param("id") Long id);
	
}

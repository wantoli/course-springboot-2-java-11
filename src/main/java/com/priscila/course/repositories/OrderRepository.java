package com.priscila.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.priscila.course.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}

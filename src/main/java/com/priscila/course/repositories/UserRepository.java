package com.priscila.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.priscila.course.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

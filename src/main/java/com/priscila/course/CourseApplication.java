package com.priscila.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(
		scanBasePackages = {"com.priscila.course"})
public class CourseApplication {
	
	private static final Logger LOG = LoggerFactory.getLogger(CourseApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(CourseApplication.class, args);
	}

}

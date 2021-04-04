package com.priscila.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(
		scanBasePackages = {"com.priscila.course"})
public class CourseApplication {
	
	private static final Logger log = LoggerFactory.getLogger(CourseApplication.class);

	@Value("${info.app.name}")
	private static String appName;
	
	public static void main(String[] args) {
		log.info("Starting application ... " + appName);
		SpringApplication.run(CourseApplication.class, args);
		log.info("Graceful shutdown ... " + appName);
	}

}

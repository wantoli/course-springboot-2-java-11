package com.priscila.course.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class BeanConfig {

	private static final String OBJECT_MAPPER_FORMAT_DATE = "yyyy-MM-dd'T'HH:mm:ss";
	private static final String OBJECT_MAPPER_TIME_ZONE = "America/Sao_Paulo";
	
	@Bean
	public ObjectMapper objectMapper() {
		
		final DateFormat df = new SimpleDateFormat(OBJECT_MAPPER_FORMAT_DATE);
		return new ObjectMapper()
				.registerModule(new JavaTimeModule())
				.setDateFormat(df)
				.setTimeZone(TimeZone.getTimeZone(OBJECT_MAPPER_TIME_ZONE));
				
	}
}

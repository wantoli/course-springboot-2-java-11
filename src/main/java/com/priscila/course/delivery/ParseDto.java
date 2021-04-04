package com.priscila.course.delivery;

import org.springframework.stereotype.Component;

import com.priscila.course.entities.User;

@Component
public class ParseDto {

	public User parseDtoToUser(UserDto dto) {
		
		User user = new User();
		
		user.setEmail(dto.getEmail());
		user.setIdUnico(dto.getIdUnico());
		user.setName(dto.getName());
		user.setPassword(dto.getPassword());
		user.setPhone(dto.getPhone());
		
		return user;
	}
	

}

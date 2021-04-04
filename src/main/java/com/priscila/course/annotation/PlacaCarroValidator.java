package com.priscila.course.annotation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PlacaCarroValidator implements ConstraintValidator<PlacaCarro, String>{

	private Pattern pattern = Pattern.compile("^[a-zA-Z]{3}\\-\\d{4}$");
	
	@Override
	public void initialize(PlacaCarro annotation) {
		
	}
	
	
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if (value == null || "".equals(value)) {
			return true;
		}
		
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
}

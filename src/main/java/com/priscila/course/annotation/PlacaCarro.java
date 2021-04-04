package com.priscila.course.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PlacaCarroValidator.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlacaCarro {

	String message() default "Formato da placa Invalido";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
}

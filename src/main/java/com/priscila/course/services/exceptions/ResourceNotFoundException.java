package com.priscila.course.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(Object id, String msg) {
		super("Resource not found " + msg + " .id " + id);
	}
}

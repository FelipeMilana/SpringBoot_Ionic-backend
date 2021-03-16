package com.javaudemy.SpringBoot_Ionic.resources.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Instant timestamp, Integer status, String error, String exception, String message, String path) {
		super(timestamp, status, error, exception, message, path);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}
}

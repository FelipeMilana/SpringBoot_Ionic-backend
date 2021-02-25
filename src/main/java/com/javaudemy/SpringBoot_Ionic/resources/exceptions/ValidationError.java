package com.javaudemy.SpringBoot_Ionic.resources.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Integer httpStatus, String msg, Instant timeStamp, String path) {
		super(httpStatus, msg, timeStamp, path);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}
}

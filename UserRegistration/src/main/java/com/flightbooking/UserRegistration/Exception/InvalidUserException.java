package com.flightbooking.UserRegistration.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidUserException extends RuntimeException{

	
		private static final long serialVersionUID = 1L;

		public InvalidUserException(String exception) {
			super(exception);	
		}
		

}

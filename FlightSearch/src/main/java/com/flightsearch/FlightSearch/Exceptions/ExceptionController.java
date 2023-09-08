package com.flightsearch.FlightSearch.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionController {

		@ExceptionHandler(value = FlightsNotFoundException.class)
		public ResponseEntity<ErrorResponse> Exceptionhandler(FlightsNotFoundException ex, WebRequest wr) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), wr.getDescription(false));
			return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
		}
		

}
package com.FlightBooking.TicketBooking.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(value = FlightNotFoundException.class)
	public ResponseEntity<ErrorResponse> Exceptionhandler(FlightNotFoundException ex, WebRequest wr) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), wr.getDescription(false));
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> Exceptionhandler(UserNotFoundException ex, WebRequest wr) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), wr.getDescription(false));
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = TicketNotBookedException.class)
	public ResponseEntity<ErrorResponse> Exceptionhandler(TicketNotBookedException ex, WebRequest wr) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), wr.getDescription(false));
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = NoPreviousBookingsException.class)
	public ResponseEntity<ErrorResponse> Exceptionhandler(NoPreviousBookingsException ex, WebRequest wr) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), wr.getDescription(false));
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
}

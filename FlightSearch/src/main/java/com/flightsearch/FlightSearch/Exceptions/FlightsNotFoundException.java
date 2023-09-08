package com.flightsearch.FlightSearch.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FlightsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FlightsNotFoundException(String exception) {
		super(exception);
	}

}

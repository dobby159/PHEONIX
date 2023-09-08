package com.flightsearch.FlightSearch.Exceptions;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="error")
public class ErrorResponse {
	
	public String message;
	public String details;

	public ErrorResponse(String message, String details) {
		super();
		this.message = message;
		this.details = details;
	}


}

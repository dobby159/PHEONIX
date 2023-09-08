package com.flightbooking.UserRegistration.ResponseDto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class UserRegistrationResponseDto {
	public int statusCode;
	public String message;
	public int id;

	public UserRegistrationResponseDto(String message, int id, int statusCode) {
		super();
		this.message = message;
		this.id = id;
		this.statusCode = statusCode;
	}

	public UserRegistrationResponseDto(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
}

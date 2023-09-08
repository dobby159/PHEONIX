package com.flightbooking.UserRegistration.Service;

public interface EmailService {
	
	public void sendSimpleMessage(String phoneNo, String toMail, String subject, String text);

}

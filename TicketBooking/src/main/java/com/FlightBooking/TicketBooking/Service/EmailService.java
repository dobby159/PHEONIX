package com.FlightBooking.TicketBooking.Service;

public interface EmailService {

	
	public void sendSimpleMessage(Integer userId, String toMail, String subject, String text);

}

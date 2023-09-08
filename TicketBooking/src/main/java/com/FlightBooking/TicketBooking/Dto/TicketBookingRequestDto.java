package com.FlightBooking.TicketBooking.Dto;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TicketBookingRequestDto {
	
	Integer flightId;
	Integer userId;
	int ticketCount;
	String category;
	String dateOfJourney;
	String emailId;
	
	List<PassengerRequestDto> passengers;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public Integer getFlightId() {
		return flightId;
	}
	public void setFlightId(Integer flightId) {
		this.flightId = flightId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public int getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDateOfJourney() {
		return dateOfJourney;
	}
	public void setDateOfJourney(String dateOfJourney) {
		this.dateOfJourney = dateOfJourney;
	}
	public List<PassengerRequestDto> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<PassengerRequestDto> passengers) {
		this.passengers = passengers;
	}

}

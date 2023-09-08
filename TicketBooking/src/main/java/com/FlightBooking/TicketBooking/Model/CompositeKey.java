package com.FlightBooking.TicketBooking.Model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CompositeKey implements Serializable {
	
	private Integer passengerId;
	private Integer ticketId;
	
	
	public CompositeKey()
	{
		
	}
	
	public Integer getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(Integer passengerId) {
		this.passengerId = passengerId;
	}
	public Integer getTicketId() {
		return ticketId;
	}
	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public CompositeKey(Integer passengerId, Integer ticketId) {
		super();
		this.passengerId = passengerId;
		this.ticketId = ticketId;
	}
	

}

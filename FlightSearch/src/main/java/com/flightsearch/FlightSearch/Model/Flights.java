package com.flightsearch.FlightSearch.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Flights {
	
	@Id
	Integer flightId;
	
	String flightName;
	String departureTime;
	String arrivalTime;
	String dateOfJourney;
	String source;
	String destination;
	
	public String getDateofjourney() {
		return dateOfJourney;
	}
	public void setDateofjourney(String dateofjourney) {
		this.dateOfJourney = dateofjourney;
	}
	public Integer getFlightId() {
		return flightId;
	}
	public void setFlightId(Integer flightId) {
		this.flightId = flightId;
	}
	public String getFlightName() {
		return flightName;
	}
	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}


}

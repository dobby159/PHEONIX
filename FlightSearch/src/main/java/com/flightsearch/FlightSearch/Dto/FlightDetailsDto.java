package com.flightsearch.FlightSearch.Dto;

import org.springframework.stereotype.Component;

@Component
public class FlightDetailsDto {
	
	
	Integer flightId;
	String flightName;
	String departureTime;
	String arrivalTime;
	String source;
	String destination;
	int economySeats;
	int economyCost;
	int businessSeats;
	int businessCost;
	
	
	public int getEconomySeats() {
		return economySeats;
	}
	public void setEconomySeats(int economySeats) {
		this.economySeats = economySeats;
	}
	public int getEconomyCost() {
		return economyCost;
	}
	public void setEconomyCost(int economyCost) {
		this.economyCost = economyCost;
	}
	public int getBusinessSeats() {
		return businessSeats;
	}
	public void setBusinessSeats(int businessSeats) {
		this.businessSeats = businessSeats;
	}
	public int getBusinessCost() {
		return businessCost;
	}
	public void setBusinessCost(int businessCost) {
		this.businessCost = businessCost;
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

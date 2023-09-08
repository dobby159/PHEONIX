package com.flightsearch.FlightSearch.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Category {
	
	@Id
	Integer flightId;
	
	int economySeats;
	int economyCost;
	int businessSeats;
	int businessCost;
	
	public Integer getFlightId() {
		return flightId;
	}
	public void setFlightId(Integer flightId) {
		this.flightId = flightId;
	}
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

}

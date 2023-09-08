package com.FlightBooking.TicketBooking.Model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Passenger {
	
	@EmbeddedId
	private CompositeKey compositeKey;
	
	String name;
	int age;
	
	public CompositeKey getCompositeKey() {
		return compositeKey;
	}
	public void setCompositeKey(CompositeKey compositeKey) {
		this.compositeKey = compositeKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	

}

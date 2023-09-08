package com.flightsearch.FlightSearch.Service;

import java.util.List;
import org.springframework.http.ResponseEntity;

import com.flightsearch.FlightSearch.Dto.CategoryResponseDto;
import com.flightsearch.FlightSearch.Dto.FlightDetailsDto;
import com.flightsearch.FlightSearch.Dto.FlightSearchResponseDto;


public interface FlightService {	
	
	public ResponseEntity<List<FlightDetailsDto>> flightSearch(String source, String destination, String dateOfJourney);
	
	public ResponseEntity<List<FlightDetailsDto>> filterByName(String flightName);
	
	public ResponseEntity<List<FlightSearchResponseDto>> flightSearchById(Integer flightId);
	
	//public ResponseEntity<List<CategoryResponseDto>> seatAvailability(Integer flightId);
	
	public ResponseEntity<List<FlightDetailsDto>> filterByCost(Integer cost, String category);
	
	public void updateSeats(Integer flightId, String category, int ticketCount);
	
	public void ticketCancellation(int ticketCount,Integer flightId,String category);

}

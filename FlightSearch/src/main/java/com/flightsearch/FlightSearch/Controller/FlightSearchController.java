package com.flightsearch.FlightSearch.Controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightsearch.FlightSearch.Dto.CategoryResponseDto;
import com.flightsearch.FlightSearch.Dto.FlightDetailsDto;
import com.flightsearch.FlightSearch.Dto.FlightSearchResponseDto;
import com.flightsearch.FlightSearch.Service.FlightService;

import ch.qos.logback.classic.Logger;

@RestController
public class FlightSearchController {

	@Autowired
	FlightService flightService;

	private final Logger logger = (Logger) LoggerFactory.getLogger(FlightSearchController.class);

	@GetMapping("/flights")
	public ResponseEntity<List<FlightDetailsDto>> flightSearch(@RequestParam("source") String source,
			@RequestParam("dateOfJourney") String dateOfJourney, @RequestParam("destination") String destination) {

		logger.info("Searching flights");
		ResponseEntity<List<FlightDetailsDto>> flightsFiltered = flightService.flightSearch(source, destination,
				dateOfJourney);
		return flightsFiltered;
	}

	@GetMapping("/flights/flightName")
	public ResponseEntity<List<FlightDetailsDto>> flightFilter(@RequestParam("flightName") String flightName) {

		logger.info("Filter By Name");
		ResponseEntity<List<FlightDetailsDto>> flightDetails = flightService.filterByName(flightName);
		return flightDetails;
	}

	@GetMapping("/flights/cost")
	public ResponseEntity<List<FlightDetailsDto>> flightFilter(@RequestParam("category") String category,
			@RequestParam("cost") int cost) {

		logger.info("Filter By Name");
		ResponseEntity<List<FlightDetailsDto>> flightList = flightService.filterByCost(cost, category);
		return flightList;
	}

	@GetMapping("/flights/{flightId}")
	public ResponseEntity<List<FlightSearchResponseDto>> flightSearchById(@PathVariable("flightId") Integer flightId) {

		logger.info("Searching flights by flightId");

		ResponseEntity<List<FlightSearchResponseDto>> flightsById = flightService.flightSearchById(flightId);
		return flightsById;
	}

//	@GetMapping("/flights/{flightId}/seats")
//
//	public ResponseEntity<List<CategoryResponseDto>> seatAvailability(@PathVariable("flightId") Integer flightId) {
//
//		logger.info("Seat Availability");
//		ResponseEntity<List<CategoryResponseDto>> seatsList = flightService.seatAvailability(flightId);
//		return seatsList;
//	}

	@PostMapping("/seatUpdate")
	public void seatUpdate(@RequestParam("flightId") Integer flightId, @RequestParam("category") String category,
			@RequestParam("ticketCount") int ticketCount) {

		logger.info("Updating Seats");

		flightService.updateSeats(flightId, category, ticketCount);

	}

	@PostMapping("/ticketCancellation")
	public void ticketCancellation(@RequestParam("flightId") Integer flightId, @RequestParam("category") String category,
			@RequestParam("ticketCount") int ticketCount) {

		logger.info("Updating Seats");

		flightService.ticketCancellation(ticketCount, flightId, category);

	}
	
	
	
	
	
	
	
	
	
	
	
	
}

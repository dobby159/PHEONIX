package com.flightsearch.FlightSearch.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.flightsearch.FlightSearch.Dto.CategoryResponseDto;
import com.flightsearch.FlightSearch.Dto.FlightDetailsDto;
import com.flightsearch.FlightSearch.Dto.FlightSearchResponseDto;
import com.flightsearch.FlightSearch.Exceptions.FlightsNotFoundException;
import com.flightsearch.FlightSearch.Model.Category;
import com.flightsearch.FlightSearch.Model.Flights;
import com.flightsearch.FlightSearch.dao.CategoryRepository;
import com.flightsearch.FlightSearch.dao.FlightSearchRepository;

@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	FlightSearchRepository searchRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ModelMapper modelMapper;

	List<FlightDetailsDto> filteredList = new ArrayList<FlightDetailsDto>();

	FlightSearchResponseDto flightSearchResponseDto = new FlightSearchResponseDto();

	/*
	 * searching the flights based on source, destination and date of journey using
	 * method
	 */

	@Override
	public ResponseEntity<List<FlightSearchResponseDto>> flightSearchById(Integer flightId) {

		Flights flightsById = searchRepository.findByFlightId(flightId);

		Category categoryListById = categoryRepository.findByFlightId(flightId);

		if (flightsById == null)
			throw new FlightsNotFoundException("no flights");

		flightSearchResponseDto.setFlightId(flightsById.getFlightId());
		flightSearchResponseDto.setArrivalTime(flightsById.getArrivalTime());
		flightSearchResponseDto.setDepartureTime(flightsById.getDepartureTime());
		flightSearchResponseDto.setDestination(flightsById.getDestination());
		flightSearchResponseDto.setSource(flightsById.getSource());
		flightSearchResponseDto.setFlightName(flightsById.getFlightName());

		flightSearchResponseDto.setBusinessCost(categoryListById.getBusinessCost());
		flightSearchResponseDto.setBusinessSeats(categoryListById.getBusinessSeats());
		flightSearchResponseDto.setEconomyCost(categoryListById.getEconomyCost());
		flightSearchResponseDto.setEconomySeats(categoryListById.getEconomySeats());

//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//		flightSearchResponseDto = modelMapper.map(categoryListById, FlightSearchResponseDto.class);
//		
		List<FlightSearchResponseDto> listOfFlights = new ArrayList<FlightSearchResponseDto>();

		listOfFlights.add(flightSearchResponseDto);
		return new ResponseEntity<List<FlightSearchResponseDto>>(listOfFlights, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<FlightDetailsDto>> flightSearch(String source, String destination,
			String dateOfJourney) {

		filteredList.clear();

		List<Flights> searchFlights = searchRepository.findBySourceAndDestinationAndDateOfJourney(source, destination,
				dateOfJourney);

		if (searchFlights.isEmpty()) {
			throw new FlightsNotFoundException("flights with the given source and destination are not found");
		}

		// for (Flights flightFilters: filteredFlightDetails) {

		searchFlights.stream().map(flights -> getFlightDetails(flights)).collect(Collectors.toList());

		return new ResponseEntity<List<FlightDetailsDto>>(filteredList, HttpStatus.OK);
	}

	public List<FlightDetailsDto> getFlightDetails(Flights listOfFlights) {

		Optional<Category> categoryList = categoryRepository.findById(listOfFlights.getFlightId());

		FlightDetailsDto flightDetailsDto = modelMapper.map(listOfFlights, FlightDetailsDto.class);

		flightDetailsDto.setEconomyCost(categoryList.get().getEconomyCost());
		flightDetailsDto.setEconomySeats(categoryList.get().getEconomySeats());
		flightDetailsDto.setBusinessCost(categoryList.get().getBusinessCost());
		flightDetailsDto.setBusinessSeats(categoryList.get().getBusinessSeats());

		filteredList.add(flightDetailsDto);

		return filteredList;
	}

	/* filtering the flight details based on flight name */

	@Override
	public ResponseEntity<List<FlightDetailsDto>> filterByName(String flightName) {

		List<FlightDetailsDto> flightFilterbyName = filteredList.stream()
				.filter(flight -> flight.getFlightName().equalsIgnoreCase(flightName)).collect(Collectors.toList());

		if (flightFilterbyName.isEmpty())
			throw new FlightsNotFoundException("flights with given name are not found");

		return new ResponseEntity<List<FlightDetailsDto>>(flightFilterbyName, HttpStatus.OK);
	}

//	@Override
//	public ResponseEntity<List<CategoryResponseDto>> seatAvailability(Integer flightId) {
//
//		Category seatList = categoryRepository.findByFlightId(flightId);
//
//		if (seatList == null)
//			throw new FlightsNotFoundException("enter a valid flightId");
//
//		CategoryResponseDto categoryDto = modelMapper.map(seatList, CategoryResponseDto.class);
//
//		List<CategoryResponseDto> categoryList = new ArrayList<CategoryResponseDto>();
//
//		categoryList.add(categoryDto);
//
//		return new ResponseEntity<List<CategoryResponseDto>>(categoryList, HttpStatus.OK);
//	}

	@Override
	public void updateSeats(Integer flightId, String category, int ticketCount) {

		int seats;

		Category updatedSeat = categoryRepository.findByFlightId(flightId);

		if (category.equals("Economy")) {
			seats = (updatedSeat.getEconomySeats()) - ticketCount;
			updatedSeat.setEconomySeats(seats);
			categoryRepository.save(updatedSeat);
		} else {
			seats = (updatedSeat.getBusinessSeats()) - ticketCount;
			updatedSeat.setBusinessSeats(seats);
			categoryRepository.save(updatedSeat);
		}

	}

	@Override
	public ResponseEntity<List<FlightDetailsDto>> filterByCost(Integer cost, String category) {

		if (category.equals("Economy")) {
			List<FlightDetailsDto> economyList = filteredList.stream()
					.filter(flightList -> flightList.getEconomyCost() < cost).collect(Collectors.toList());
			return new ResponseEntity<List<FlightDetailsDto>>(economyList, HttpStatus.OK);
		}

		else {
			List<FlightDetailsDto> businessList = filteredList.stream()
					.filter(flightList -> flightList.getBusinessCost() < cost).collect(Collectors.toList());
			return new ResponseEntity<List<FlightDetailsDto>>(businessList, HttpStatus.OK);
		}

	}

	@Override
	public void ticketCancellation(int ticketCount, Integer flightId, String category) {
		
		int numberOfTickets;
		
		Category cancelledList= categoryRepository.findByFlightId(flightId);
		
		if(category.equalsIgnoreCase("economy"))
		{
			numberOfTickets= (cancelledList.getEconomySeats())+(ticketCount);
			cancelledList.setEconomySeats(numberOfTickets);
			categoryRepository.save(cancelledList);
		}
		else
		{
			numberOfTickets= (cancelledList.getBusinessSeats())+(ticketCount);
			cancelledList.setBusinessSeats(numberOfTickets);
			categoryRepository.save(cancelledList);
		}
	
	}
}

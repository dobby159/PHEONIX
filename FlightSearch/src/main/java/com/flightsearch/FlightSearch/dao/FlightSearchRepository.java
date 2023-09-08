package com.flightsearch.FlightSearch.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.flightsearch.FlightSearch.Model.Flights;

@Repository
public interface FlightSearchRepository extends CrudRepository<Flights, Integer>{

	
	public List<Flights> findBySourceAndDestinationAndDateOfJourney(String source, String destination,String dateOfJourney);

	public List<Flights> findByFlightName(String flightName);

	public Flights findByFlightId(Integer flightId);

}

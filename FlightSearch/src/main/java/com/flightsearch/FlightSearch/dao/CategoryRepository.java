package com.flightsearch.FlightSearch.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.flightsearch.FlightSearch.Model.Category;


@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer>{

	public Category findByFlightId(Integer flightId);
	

}

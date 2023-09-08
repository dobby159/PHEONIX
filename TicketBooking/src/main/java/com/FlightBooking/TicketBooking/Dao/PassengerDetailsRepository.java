package com.FlightBooking.TicketBooking.Dao;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.FlightBooking.TicketBooking.Model.CompositeKey;
import com.FlightBooking.TicketBooking.Model.Passenger;

@Repository
public interface PassengerDetailsRepository extends CrudRepository<Passenger,CompositeKey> {
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="delete from passenger where ticket_id=:ticket_id", nativeQuery=true)
	public void deleteByTicketId(@PathParam("ticket_id") Integer ticket_id);
	

}

package com.FlightBooking.TicketBooking.Dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.FlightBooking.TicketBooking.Model.Ticket;

@Repository
public interface TicketBookingRepository extends CrudRepository<Ticket,Integer>{
	
	public List<Ticket> findByUserId(Integer userId);

	public Optional<Ticket> findByTicketId(Integer ticketId);

	public Ticket findByUserIdAndFlightId(Integer userId, Integer flightId);
}

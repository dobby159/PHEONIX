package com.FlightBooking.TicketBooking.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.FlightBooking.TicketBooking.Dto.BookingHistoryResponseDto;
import com.FlightBooking.TicketBooking.Dto.TicketBookingRequestDto;
import com.FlightBooking.TicketBooking.Dto.TicketDetailsResponseDto;

public interface BookingService {
	
	public ResponseEntity<Integer> saveTicketDetails(TicketBookingRequestDto ticketDto);

	public void checkSeatAvailability(TicketBookingRequestDto ticketDto);
	
	public ResponseEntity<List<BookingHistoryResponseDto>> myBookings(Integer userId);
	
	public ResponseEntity<List<TicketDetailsResponseDto>> getTicketDetails(Integer ticketId);
	
	public void ticketCancellation(Integer ticketId);
}

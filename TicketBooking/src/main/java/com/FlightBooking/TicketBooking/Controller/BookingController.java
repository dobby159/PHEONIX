package com.FlightBooking.TicketBooking.Controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.FlightBooking.TicketBooking.Dto.BookingHistoryResponseDto;
import com.FlightBooking.TicketBooking.Dto.TicketBookingRequestDto;
import com.FlightBooking.TicketBooking.Dto.TicketDetailsResponseDto;
import com.FlightBooking.TicketBooking.Service.BookingService;
import com.FlightBooking.TicketBooking.Service.EmailService;

import ch.qos.logback.classic.Logger;

@RestController
public class BookingController {

	@Autowired
	BookingService bookingService;

	@Autowired
	EmailService emailService;

	private final Logger logger = (Logger) LoggerFactory.getLogger(BookingController.class);

	@PostMapping("/ticketBooking")
	public ResponseEntity<Integer> saveTicketDetails(@RequestBody TicketBookingRequestDto ticketDto) {

		logger.info("Ticket Booking");

		bookingService.checkSeatAvailability(ticketDto);
		emailService.sendSimpleMessage(ticketDto.getUserId(), ticketDto.getEmailId(), "BOOKING CONFIRMATION",
				"your ticket is booked. Ticket Details are ...flightId :" + ticketDto.getFlightId() + "& Travelling on :" + ticketDto.getDateOfJourney());
		return bookingService.saveTicketDetails(ticketDto);
	}

	@GetMapping("/myBookings/{userId}")
	public ResponseEntity<List<BookingHistoryResponseDto>> userBookings(@PathVariable("userId") Integer userId) {

		logger.info("Booking History");

		ResponseEntity<List<BookingHistoryResponseDto>> bookingsList = bookingService.myBookings(userId);
		return bookingsList;
	}

	@GetMapping("/tickets/{ticketId}")
	public ResponseEntity<List<TicketDetailsResponseDto>> ticketDetails(@PathVariable("ticketId") Integer ticketId) {

		logger.info("Ticket Details");

		ResponseEntity<List<TicketDetailsResponseDto>> list = bookingService.getTicketDetails(ticketId);
		return list;
	}

	@DeleteMapping("/cancelTicket/{ticketId}")
	public ResponseEntity<String> ticketCancellation(@PathVariable("ticketId") Integer ticketId) {

		logger.info("ticket Cancellation");
		
		bookingService.ticketCancellation(ticketId);

		return new ResponseEntity<String> ("ticket is cancelled", HttpStatus.OK);
	}

}

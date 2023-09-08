package com.flightbooking.UserRegistration.Controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.flightbooking.UserRegistration.ResponseDto.BookingHistoryResponseDto;
import com.flightbooking.UserRegistration.ResponseDto.FlightSearchResponseDto;
import com.flightbooking.UserRegistration.dto.FlightDetailsDto;
import com.flightbooking.UserRegistration.dto.TicketBookingRequestDto;

import ch.qos.logback.classic.Logger;

@RestController
public class GatewayController {

	@Autowired
	RestTemplate restTemplate;

	private final Logger logger = (Logger) LoggerFactory.getLogger(RegistrationController.class);

	@GetMapping("/flights")
	public ResponseEntity<List<FlightDetailsDto>> flightSearch(@RequestParam("source") String source,
			@RequestParam("dateOfJourney") String dateOfJourney, @RequestParam("destination") String destination) {

		logger.info("Searching flights");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ParameterizedTypeReference<List<FlightDetailsDto>> parameterizedTypeReference = new ParameterizedTypeReference<List<FlightDetailsDto>>() {
		};
		HttpEntity<List<FlightDetailsDto>> entity = new HttpEntity<List<FlightDetailsDto>>(headers);

		ResponseEntity<List<FlightDetailsDto>> searchFlights = restTemplate
				.exchange(
						"http://FLIGHTSEARCH/flightsearch/flights?source=" + source + "&destination=" + destination
								+ "&dateOfJourney=" + dateOfJourney,
						HttpMethod.GET, entity, parameterizedTypeReference);

		return searchFlights;
	}

	@GetMapping("/flights/flightName")
	public ResponseEntity<List<FlightDetailsDto>> flightFilter(@RequestParam("flightName") String flightName) {

		logger.info("Filter By Name");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ParameterizedTypeReference<List<FlightDetailsDto>> parameterizedTypeReference = new ParameterizedTypeReference<List<FlightDetailsDto>>() {
		};
		HttpEntity<List<FlightDetailsDto>> entity = new HttpEntity<List<FlightDetailsDto>>(headers);

		ResponseEntity<List<FlightDetailsDto>> flightDetails = restTemplate.exchange(
				"http://FLIGHTSEARCH/flightsearch/flights/flightName?flightName=" + flightName, HttpMethod.GET, entity,
				parameterizedTypeReference);

		return flightDetails;
	}

	@GetMapping("/flights/cost")
	public ResponseEntity<List<FlightDetailsDto>> flightFilter(@RequestParam("category") String category,
			@RequestParam("cost") int cost) {

		logger.info("Filter By Name");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ParameterizedTypeReference<List<FlightDetailsDto>> parameterizedTypeReference = new ParameterizedTypeReference<List<FlightDetailsDto>>() {
		};
		HttpEntity<List<FlightDetailsDto>> entity = new HttpEntity<List<FlightDetailsDto>>(headers);

		ResponseEntity<List<FlightDetailsDto>> flightList = restTemplate.exchange(
				"http://FLIGHTSEARCH/flightsearch/flights/cost?category=" + category + "&cost=" + cost, HttpMethod.GET,
				entity, parameterizedTypeReference);

		return flightList;
	}

	@GetMapping("/flights/{flightId}")
	public ResponseEntity<List<FlightSearchResponseDto>> flightSearchById(@PathVariable("flightId") Integer flightId) {

		logger.info("Searching flights by flightId");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ParameterizedTypeReference<List<FlightSearchResponseDto>> parameterizedTypeReference = new ParameterizedTypeReference<List<FlightSearchResponseDto>>() {
		};
		HttpEntity<List<FlightSearchResponseDto>> entity = new HttpEntity<List<FlightSearchResponseDto>>(headers);

		ResponseEntity<List<FlightSearchResponseDto>> flightsById = restTemplate.exchange(
				"http://FLIGHTSEARCH/flightsearch/flights/" + flightId, HttpMethod.GET, entity,
				parameterizedTypeReference);

		return flightsById;
	}

//	@PostMapping("/ticketBooking")
//	public ResponseEntity<List<TicketBookingRequestDto>> saveTicketDetails(@RequestBody TicketBookingRequestDto ticketDto) {
//
//		logger.info("Ticket Booking");
//
//		HttpHeaders headers = new HttpHeaders();
//		ParameterizedTypeReference<List<TicketBookingRequestDto>> parameterizedTypeReference = new ParameterizedTypeReference<List<TicketBookingRequestDto>>() {
//		};
//		HttpEntity<List<TicketBookingRequestDto>> entity = new HttpEntity<List<TicketBookingRequestDto>>(headers);
//
//		return restTemplate.exchange("http://TICKETBOOKING/bookticket/ticketBooking",HttpMethod.POST,entity, parameterizedTypeReference);
//	}
	@PostMapping("/ticketBooking")
	public ResponseEntity<Integer> saveTicketDetails(@RequestBody TicketBookingRequestDto ticketDto) {

		logger.info("Ticket Booking");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return restTemplate.postForEntity("http://TICKETBOOKING/bookticket/ticketBooking", ticketDto, Integer.class);

	}

	@DeleteMapping("/cancelTicket/{ticketId}")
	public ResponseEntity<String> ticketCancellation(@PathVariable("ticketId") Integer ticketId) {

		logger.info("ticket Cancellation");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return restTemplate.exchange("http://TICKETBOOKING/bookticket/cancelTicket/" + ticketId, HttpMethod.DELETE,entity,String.class);
	}

	@GetMapping("/myBookings/{userId}")
	public ResponseEntity<List<BookingHistoryResponseDto>> userBookings(@PathVariable("userId") Integer userId) {

		logger.info("Booking History");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ParameterizedTypeReference<List<BookingHistoryResponseDto>> parameterizedTypeReference = new ParameterizedTypeReference<List<BookingHistoryResponseDto>>() {
		};
		HttpEntity<List<BookingHistoryResponseDto>> entity = new HttpEntity<List<BookingHistoryResponseDto>>(headers);

		ResponseEntity<List<BookingHistoryResponseDto>> bookingsList = restTemplate.exchange(
				"http://TICKETBOOKING/bookticket/myBookings/" + userId, HttpMethod.GET, entity,
				parameterizedTypeReference);
		return bookingsList;
	}

}

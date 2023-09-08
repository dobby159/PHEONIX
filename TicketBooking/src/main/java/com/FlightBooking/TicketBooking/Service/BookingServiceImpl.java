package com.FlightBooking.TicketBooking.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.FlightBooking.TicketBooking.Dao.PassengerDetailsRepository;
import com.FlightBooking.TicketBooking.Dao.TicketBookingRepository;
import com.FlightBooking.TicketBooking.Dto.BookingHistoryResponseDto;
import com.FlightBooking.TicketBooking.Dto.CategoryResponseDto;
import com.FlightBooking.TicketBooking.Dto.PassengerRequestDto;
import com.FlightBooking.TicketBooking.Dto.TicketBookingRequestDto;
import com.FlightBooking.TicketBooking.Dto.TicketDetailsResponseDto;
import com.FlightBooking.TicketBooking.Exception.NoPreviousBookingsException;
import com.FlightBooking.TicketBooking.Exception.TicketNotBookedException;
import com.FlightBooking.TicketBooking.Model.CompositeKey;
import com.FlightBooking.TicketBooking.Model.Passenger;
import com.FlightBooking.TicketBooking.Model.Ticket;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	TicketBookingRepository ticketRepository;

	@Autowired
	PassengerDetailsRepository passengerRepository;

	@Autowired
	RestTemplate restTemplate;

	static Integer ticketId = 4100;
	static Integer passengerId = 3100;

	List<BookingHistoryResponseDto> userBookings = new ArrayList<BookingHistoryResponseDto>();

	@Override
	public ResponseEntity<Integer> saveTicketDetails(TicketBookingRequestDto ticketDto) {

		Ticket ticket = new Ticket();
		Passenger passengers = new Passenger();
		CompositeKey compositeKey = new CompositeKey();

		Integer ticketIdNew = ticketId++;

		ticket.setTicketId(ticketIdNew);

		ticket.setUserId(ticketDto.getUserId());
		ticket.setCategory(ticketDto.getCategory());
		ticket.setDateOfJourney(ticketDto.getDateOfJourney());
		ticket.setFlightId(ticketDto.getFlightId());
		ticket.setEmailId(ticketDto.getEmailId());
		ticket.setTicketCount(ticketDto.getTicketCount());

		HttpHeaders headers = new HttpHeaders();
		ParameterizedTypeReference<List<TicketDetailsResponseDto>> parameterizedTypeReference = new ParameterizedTypeReference<List<TicketDetailsResponseDto>>() {
		};
		HttpEntity<List<TicketDetailsResponseDto>> entity = new HttpEntity<List<TicketDetailsResponseDto>>(headers);

		ResponseEntity<List<TicketDetailsResponseDto>> userDetails = restTemplate.exchange(
				"http://USERREGISTRATION/registration/users/" + ticketDto.getUserId(), HttpMethod.GET,
				entity, parameterizedTypeReference);

		ResponseEntity<List<TicketDetailsResponseDto>> ticketDetails = restTemplate.exchange(
				"http://FLIGHTSEARCH/flightsearch/flights/" + ticketDto.getFlightId(),
				HttpMethod.GET, entity, parameterizedTypeReference);

		ticket.setSource(ticketDetails.getBody().get(0).getSource());
		ticket.setDestination(ticketDetails.getBody().get(0).getDestination());

		List<PassengerRequestDto> listOfPassengers = ticketDto.getPassengers();

		int size = ticket.getTicketCount();

		for (int i = 0; i < size; i++) {
			compositeKey.setPassengerId(passengerId++);
			compositeKey.setTicketId(ticketIdNew);

			String name = listOfPassengers.get(i).getName();
			int age = listOfPassengers.get(i).getAge();

			passengers.setAge(age);
			passengers.setName(name);
			passengers.setCompositeKey(compositeKey);

			passengerRepository.save(passengers);
			ticketRepository.save(ticket);
		}
		return new ResponseEntity<Integer> (ticketIdNew,HttpStatus.OK);
	}

	@Override
	public void checkSeatAvailability(TicketBookingRequestDto ticketDto) {

		HttpHeaders headers = new HttpHeaders();
		ParameterizedTypeReference<List<CategoryResponseDto>> parameterizedTypeReference = new ParameterizedTypeReference<List<CategoryResponseDto>>() {
		};
		HttpEntity<List<CategoryResponseDto>> entity = new HttpEntity<List<CategoryResponseDto>>(headers);

		restTemplate.exchange(
				"http://FLIGHTSEARCH/flightsearch/seatUpdate?flightId=" + ticketDto.getFlightId() + "&category="
						+ ticketDto.getCategory() + "&ticketCount=" + ticketDto.getTicketCount(),
				HttpMethod.POST, entity, parameterizedTypeReference);

	}

	@Override
	public ResponseEntity<List<BookingHistoryResponseDto>> myBookings(Integer userId) {
		
		userBookings.clear();

		List<Ticket> bookings = ticketRepository.findByUserId(userId);

		if (bookings.isEmpty())
			throw new NoPreviousBookingsException("there are no previous Bookings with this userId");

		bookings.stream().map(tickets -> getBookingDetails(tickets)).collect(Collectors.toList());

		return new ResponseEntity<List<BookingHistoryResponseDto>>(userBookings, HttpStatus.OK);
	}

	public List<BookingHistoryResponseDto> getBookingDetails(Ticket tickets) {

		Optional<Ticket> l = ticketRepository.findByTicketId(tickets.getTicketId());

		BookingHistoryResponseDto previousBookings = new BookingHistoryResponseDto();

		previousBookings.setDateOfJourney(l.get().getDateOfJourney());
		previousBookings.setFlightId(l.get().getFlightId());
		previousBookings.setTicketCount(l.get().getTicketCount());
		previousBookings.setTicketId(l.get().getTicketId());
		previousBookings.setUserId(l.get().getUserId());

		userBookings.add(previousBookings);
		return userBookings;

	}

	@Override
	public ResponseEntity<List<TicketDetailsResponseDto>> getTicketDetails(Integer ticketId) {

		Optional<Ticket> ticketDetails = ticketRepository.findByTicketId(ticketId);

		if (!(ticketDetails.isPresent()))
			throw new TicketNotBookedException("ticket is not booked");

		TicketDetailsResponseDto bookingDetails = new TicketDetailsResponseDto();

		bookingDetails.setUserId(ticketDetails.get().getUserId());
		bookingDetails.setDateOfJourney(ticketDetails.get().getDateOfJourney());
		bookingDetails.setFlightId(ticketDetails.get().getFlightId());
		bookingDetails.setSource(ticketDetails.get().getSource());
		bookingDetails.setDestination(ticketDetails.get().getDestination());
		bookingDetails.setCategory(ticketDetails.get().getCategory());
		bookingDetails.setTicketCount(ticketDetails.get().getTicketCount());

		List<TicketDetailsResponseDto> list = new ArrayList<TicketDetailsResponseDto>();

		list.add(bookingDetails);

		return new ResponseEntity<List<TicketDetailsResponseDto>>(list, HttpStatus.OK);
	}

	@Override
	public void ticketCancellation(Integer ticketId) {

		Optional<Ticket> validation = ticketRepository.findByTicketId(ticketId);
		
		if(validation== null) throw new TicketNotBookedException("no ticket");
	
		HttpHeaders headers = new HttpHeaders();
		ParameterizedTypeReference<List<CategoryResponseDto>> parameterizedTypeReference = new ParameterizedTypeReference<List<CategoryResponseDto>>() {
		};
		HttpEntity<List<CategoryResponseDto>> entity = new HttpEntity<List<CategoryResponseDto>>(headers);

		restTemplate.exchange(
						"http://FLIGHTSEARCH/flightsearch/ticketCancellation?category=" + validation.get().getCategory() +
								"&ticketCount=" + validation.get().getTicketCount() + "&flightId=" + validation.get().getFlightId(),
						HttpMethod.POST, entity, parameterizedTypeReference);
		
		
		passengerRepository.deleteByTicketId(ticketId);
		ticketRepository.deleteById(ticketId);
		

	}
}


package com.flightbooking.UserRegistration.Controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.UserRegistration.ResponseDto.UserDetailsResponseDto;
import com.flightbooking.UserRegistration.ResponseDto.UserRegistrationResponseDto;
import com.flightbooking.UserRegistration.Service.EmailService;
import com.flightbooking.UserRegistration.Service.RegistrationService;
import com.flightbooking.UserRegistration.dto.RegistrationRequestDto;

import ch.qos.logback.classic.Logger;

@RestController
public class RegistrationController {

	@Autowired
	RegistrationService registrationService;

	@Autowired
	EmailService emailService;

	private final Logger logger = (Logger) LoggerFactory.getLogger(RegistrationController.class);

	/* user details are saved */

	@PostMapping("/users")
	public ResponseEntity<UserRegistrationResponseDto> userRegistration(
			@RequestBody RegistrationRequestDto registrationDto) {
		logger.info("User Registered");
		
		emailService.sendSimpleMessage(registrationDto.getPhoneNo(),registrationDto.getEmailId(), "registration", "Succesfully registered");

		return registrationService.savePassengerDetails(registrationDto);

	}	

	/*
	 * @RequestParam - userId and password are given as parameters and validation is done.
	 */
	@GetMapping("/users")
	public ResponseEntity<UserRegistrationResponseDto> userLogin(@RequestParam("userId") Integer userId,
			@RequestParam("password") String password) {

		logger.info("User Login");
		return registrationService.validate(userId, password);

	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<List<UserDetailsResponseDto>> userDetails(@PathVariable("userId") Integer userId) {

		logger.info("Successfull");

		ResponseEntity<List<UserDetailsResponseDto>> userDetails = registrationService.userDetails(userId);

		return userDetails;
	}

}

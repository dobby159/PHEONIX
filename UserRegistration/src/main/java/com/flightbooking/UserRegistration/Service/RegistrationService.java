package com.flightbooking.UserRegistration.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.flightbooking.UserRegistration.ResponseDto.UserDetailsResponseDto;
import com.flightbooking.UserRegistration.ResponseDto.UserRegistrationResponseDto;
import com.flightbooking.UserRegistration.dto.RegistrationRequestDto;


public interface RegistrationService {
	
	public  ResponseEntity<UserRegistrationResponseDto> savePassengerDetails(RegistrationRequestDto registrationDto);
	public  ResponseEntity<UserRegistrationResponseDto> validate(Integer userId, String password);
	public ResponseEntity<List<UserDetailsResponseDto>> userDetails(Integer userId);
	

}

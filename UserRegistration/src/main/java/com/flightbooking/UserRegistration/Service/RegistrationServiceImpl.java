package com.flightbooking.UserRegistration.Service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightbooking.UserRegistration.Exception.InvalidUserException;
import com.flightbooking.UserRegistration.Model.User;
import com.flightbooking.UserRegistration.ResponseDto.UserDetailsResponseDto;
import com.flightbooking.UserRegistration.ResponseDto.UserRegistrationResponseDto;
import com.flightbooking.UserRegistration.dao.RegistrationRepository;
import com.flightbooking.UserRegistration.dto.RegistrationRequestDto;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	RegistrationRepository registrationRepository;

	@Autowired
	ModelMapper modelMapper;

	private static int userId = 1000;
	
	/* password validation using regular expressions */
	
	
	// private static final String password_pattern =
	// "(?=.*[a-z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*])(?=.{8,})";
//	if (registrationDto.getPassword().matches(password_pattern)) {
//
//		userRegistration.setPassword(registrationDto.getPassword());
//	}
//	else {
//		throw new PasswordInvalidationException("please give a valid password");
//	}
	
	/* saves the details of  the passenger for registration and returning the his/her's userId*/

	@Override
	public ResponseEntity<UserRegistrationResponseDto> savePassengerDetails(RegistrationRequestDto registrationRequestDto) {

		User user;

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		user = modelMapper.map(registrationRequestDto, User.class);

		user.setUserId(userId++);
		registrationRepository.save(user);

		String message = "successfully registered";
		HttpStatus statusCode = HttpStatus.CREATED;
		UserRegistrationResponseDto response = new UserRegistrationResponseDto(message, user.getUserId(), statusCode.value());
		return new ResponseEntity<UserRegistrationResponseDto>(response, HttpStatus.CREATED);
	}

	
	/* Login validation is done and exception is thrown when unregistered passenger details are given
	 * returns a messages "logged in" when login is done */
	
	@Override
	public ResponseEntity<UserRegistrationResponseDto> validate(Integer userId, String password) {

		User existingUser= registrationRepository.findByUserIdAndPassword(userId,password);
		
		if(existingUser==null)  throw new InvalidUserException("Please register before logging in");

		String message = "Login is successfull";
		HttpStatus statusCode = HttpStatus.OK;
		UserRegistrationResponseDto response = new UserRegistrationResponseDto(message, statusCode.value());
		return new ResponseEntity<UserRegistrationResponseDto>(response, HttpStatus.OK);
	}


	
	
	@Override
	public ResponseEntity<List<UserDetailsResponseDto>> userDetails(Integer userId) {
		
	User user= registrationRepository.findByUserId(userId);
	
	UserDetailsResponseDto userDetailsResponseDto= new UserDetailsResponseDto();
	
	if(user==null) throw new  InvalidUserException("User not found with the given userId");
	

	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	userDetailsResponseDto = modelMapper.map(user, UserDetailsResponseDto.class);
	
	List<UserDetailsResponseDto> userList= new ArrayList<UserDetailsResponseDto>();
	userList.add(userDetailsResponseDto);
	
		return new ResponseEntity<List<UserDetailsResponseDto>> (userList, HttpStatus.OK);
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


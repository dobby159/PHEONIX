package com.flightbooking.UserRegistration.dao;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.flightbooking.UserRegistration.Model.User;


@Repository
public interface RegistrationRepository extends CrudRepository<User, Integer> {
	
	@Query("From User where name= :name and password= :password")
	public User validate(@PathParam ("name")  String name , @PathParam("password") String password);

	public User findByUserIdAndPassword(Integer userId, String password);

	public User findByUserId(Integer userId);

	public User findByPhoneNo(String phoneNo);



}

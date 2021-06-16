package com.deepthi.ecommerce.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deepthi.ecommerce.entity.Address;
import com.deepthi.ecommerce.entity.User;
import com.deepthi.ecommerce.entity.UserLogin;
import com.deepthi.ecommerce.exception.DuplicateEntryException;
import com.deepthi.ecommerce.exception.UserNotFoundException;
import com.deepthi.ecommerce.service.UserService;


@RestController
public class UserController 
{
	static Logger log = LoggerFactory.getLogger(UserController.class.getName());
	
	@Autowired
	UserService userService;
	
	@PostMapping("/users")
	public ResponseEntity<String> userRegistration(@RequestBody User user) throws DuplicateEntryException
	{
		StringBuilder message=new StringBuilder();
		
		log.info("Getting the user from database using email");
		if(userService.getUserByEmail(user.getEmail()) != null)
		{
			log.warn("User already registered");
			throw new DuplicateEntryException("User already registered");
		}
		else
		{
			userService.addUser(user);
			log.info("User registered successfully");
			message.append("Registered Successfully");
			
			return new ResponseEntity<>(message.toString(), HttpStatus.OK);
		}
	}
	
	@GetMapping("/users")
	public ResponseEntity<String> userLogIn(@RequestBody UserLogin login) throws UserNotFoundException
	{
		StringBuilder message=new StringBuilder();
		
		log.info("Getting user from database using email");
		User userByEmail = userService.getUserByEmail(login.getEmail());
		
		if(userByEmail==null || !userByEmail.getPassword().equals(login.getPassword()))
		{
			log.warn("Incorrect email or password");
			throw new UserNotFoundException("Incorrect Email or Password");
		}
		else
		{
			log.info("User logged in successfully");
			message.append("Logged in successfully\n\n");
			message.append("User Id : "+userByEmail.getUserId());
			message.append("\nName : "+userByEmail.getName());
			message.append("\nMobile Number : "+userByEmail.getMobile());
			
			return new ResponseEntity<>(message.toString(),HttpStatus.OK);
		}
	}
	
	@PostMapping("/users/{id}/addresses")
	public ResponseEntity<String> addAddress(@RequestBody Address address, @PathVariable Long id)
	{
		StringBuilder message=new StringBuilder();
		
		User user=new User();
		
		log.info("Getting user from database using user id");
		Optional<User> userById = userService.getUserById(id);
		if(userById.isPresent())
		{
			user=userById.get();
		}
		
		log.info("Adding address to the user");
		user.setAddress(address.toString());
		userService.updateUser(user);
		message.append("Address has been added\nAddress : "+user.getAddress());
		
		return new ResponseEntity<>(message.toString(),HttpStatus.OK);
	}
	
}

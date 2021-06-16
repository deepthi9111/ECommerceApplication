package com.deepthi.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.deepthi.ecommerce.entity.Address;
import com.deepthi.ecommerce.entity.UserLogin;
import com.deepthi.ecommerce.entity.User;
import com.deepthi.ecommerce.exception.DuplicateEntryException;
import com.deepthi.ecommerce.exception.UserNotFoundException;
import com.deepthi.ecommerce.service.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class UserControllerTest 
{
	@Mock
	UserService userService;
	
	@InjectMocks
	UserController userController;
	
	@Test
	@DisplayName("Registration : Positive Scenario")
	void testUserRegistration() throws DuplicateEntryException 
	{
		User user=new User(1L,"Mounika","mounika@gmail.com","mouni@1234","8985478597","Hyderabad");
		
		when(userService.getUserByEmail(user.getEmail())).thenReturn(null);
		when(userService.addUser(user)).thenReturn(user);
		
		ResponseEntity<String> userRegistration = userController.userRegistration(user);
		
		assertTrue(userRegistration.getStatusCodeValue()==200);
		
	}
	
	@Test
	@DisplayName("Registration : Negative Scenario")
	void testUserRegistration2()
	{
		User user=new User();
		user.setUserId(1L);
		user.setName("Mounika");
		user.setEmail("mounika@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("8985478597");
		user.setAddress("Hyderabad");
		
		when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
		
		DuplicateEntryException e = assertThrows(DuplicateEntryException.class, ()->userController.userRegistration(user));
		
		assertEquals("User already registered", e.getMessage());
	}

	@Test
	@DisplayName("User Login : Positive Scenario")
	void testUserLogIn() throws UserNotFoundException 
	{
		User user=new User();
		user.setUserId(1L);
		user.setName("Mounika");
		user.setEmail("mounika@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("8985478597");
		user.setAddress("Hyderabad");
		
		UserLogin login=new UserLogin("amouni1998@gmail.com","mouni@1234");
		
		when(userService.getUserByEmail(login.getEmail())).thenReturn(user);
		
		ResponseEntity<String> userLogIn = userController.userLogIn(login);
		
		assertTrue(userLogIn.getStatusCodeValue()==200);
	}
	
	@Test
	@DisplayName("User Login : Negative Scenario")
	void testUserLogin2()
	{
		UserLogin login=new UserLogin();
		login.setEmail("mounika@gmail.com");
		login.setPassword("mouni@1234");
		
		when(userService.getUserByEmail(login.getEmail())).thenReturn(null);
		
		UserNotFoundException e = assertThrows(UserNotFoundException.class, ()->userController.userLogIn(login));
		
		assertEquals("Incorrect Email or Password", e.getMessage());
	}

	@Test
	void testAddAddress() 
	{
		Address address=new Address("11-27-11","Rammurthy Pet","Kavali","524201");
		
		User user=new User();
		user.setUserId(1L);
		user.setName("Mounika");
		user.setEmail("mounika@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("8985478597");
		user.setAddress("Hyderabad");
		
		when(userService.getUserById(user.getUserId())).thenReturn(Optional.of(user));
		when(userService.updateUser(user)).thenReturn(user);
		
		userController.addAddress(address, user.getUserId());
		
		assertEquals(address.toString(), user.getAddress());
		
	}

}

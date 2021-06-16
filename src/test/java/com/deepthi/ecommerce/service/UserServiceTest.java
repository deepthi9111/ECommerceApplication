package com.deepthi.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;

import com.deepthi.ecommerce.entity.User;
import com.deepthi.ecommerce.repository.UserRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class UserServiceTest 
{
	@Mock 
	UserRepository userRepository;
	
	@InjectMocks
	UserService userService;
	
	@Test
	void testGetUserByEmail() 
	{
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("7075725533");
		user.setAddress("Kavali");
		
		when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
		
		User userByEmail = userService.getUserByEmail(user.getEmail());
		
		assertTrue(userByEmail.getUserId()==user.getUserId());
	}

	@Test
	void testAddUser() 
	{
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("7075725533");
		user.setAddress("Kavali");
		
		when(userRepository.save(user)).thenReturn(user);
		
		User addUser = userService.addUser(user);
		
		assertTrue(addUser.getUserId()==user.getUserId());
	}

	@Test
	void testGetUserById() 
	{
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("7075725533");
		user.setAddress("Kavali");
		
		when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
		
		Optional<User> userById = userService.getUserById(user.getUserId());
		
		assertTrue(userById.isPresent());
	}

	@Test
	void testUpdateUser() 
	{
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("7075725533");
		user.setAddress("Kavali");
		
		when(userRepository.save(user)).thenReturn(user);
		
		User updateUser = userService.updateUser(user);
		
		assertTrue(updateUser.getUserId()==user.getUserId());
	}

}

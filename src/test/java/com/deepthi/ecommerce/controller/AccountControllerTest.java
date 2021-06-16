package com.deepthi.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.deepthi.ecommerce.clients.TransferClient;
import com.deepthi.ecommerce.entity.Account;
import com.deepthi.ecommerce.entity.User;
import com.deepthi.ecommerce.service.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class AccountControllerTest 
{
	@Mock
	TransferClient transferClient;
	
	@Mock
	UserService userService;
	
	@InjectMocks
	AccountController accountController;
	
	@Test
	@DisplayName("Add Account : Positive Scenario")
	void testAddAccount() throws AccountNotFoundException 
	{
		Account account=new Account();
		account.setAcno(1673190501L);
		account.setBank("HDFC");
		account.setBranch("Bangalore");
		account.setIfsc("HDFC0000549");
		
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("1234567890");
		user.setAddress("Kavali");
		
		ResponseEntity<String> response=new ResponseEntity<>(HttpStatus.OK);
		
		when(userService.getUserById(user.getUserId())).thenReturn(Optional.of(user));
		when(transferClient.checkAccount(account.getAcno())).thenReturn(response);
		when(userService.updateUser(user)).thenReturn(user);
		
		ResponseEntity<String> addAccount = accountController.addAccount(account, 1L);
		
		assertTrue(addAccount.getStatusCodeValue()==200);
		
	}
	
	@Test
	@DisplayName("Add Account : Negative Scenario")
	void testAddAccount2()
	{
		Account account=new Account();
		account.setAcno(1673190501L);
		account.setBank("HDFC");
		account.setBranch("Bangalore");
		account.setIfsc("HDFC0000549");
		
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("1234567890");
		user.setAddress("Kavali");
		
		ResponseEntity<String> response=new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		when(userService.getUserById(user.getUserId())).thenReturn(Optional.of(user));
		when(transferClient.checkAccount(account.getAcno())).thenReturn(response);
		
		AccountNotFoundException e = assertThrows(AccountNotFoundException.class, ()->accountController.addAccount(account, 1L));
		
		assertEquals("The account you want to add doesn't exist", e.getMessage());
		
	}

}

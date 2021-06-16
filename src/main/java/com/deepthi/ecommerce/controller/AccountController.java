package com.deepthi.ecommerce.controller;

import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deepthi.ecommerce.clients.TransferClient;
import com.deepthi.ecommerce.entity.Account;
import com.deepthi.ecommerce.entity.User;
import com.deepthi.ecommerce.service.UserService;

@RestController
@Transactional
public class AccountController 
{
	@Autowired
	TransferClient transferClient;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/users/{id}/accounts")
	public ResponseEntity<String> addAccount(@RequestBody Account account,@PathVariable Long id) throws AccountNotFoundException
	{
		StringBuilder message=new StringBuilder();
		
		User user=new User();
		
		Optional<User> userById = userService.getUserById(id);
		if(userById.isPresent())
		{
			user=userById.get();
		}
		System.out.println("In Accounts Before "+account.getAcno());
		ResponseEntity<String> checkAccount = transferClient.checkAccount(account.getAcno());
		System.out.println("In Accounts "+account.getAcno());
		if(checkAccount.getStatusCodeValue()==404)
		{
			throw new AccountNotFoundException("The account you want to add doesn't exist");
		}
		else
		{
			user.setAccount(account);
			userService.updateUser(user);
			message.append("Account has been added");
		}		
		
		return new ResponseEntity<>(message.toString(),HttpStatus.OK);
	}
	
	@GetMapping("/accounts")
	public ResponseEntity<String> getSample()
	{
		ResponseEntity<String> sample = transferClient.sample();
		String message=sample.getBody();
		
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}
}

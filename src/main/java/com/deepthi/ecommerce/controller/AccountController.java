package com.deepthi.ecommerce.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deepthi.ecommerce.clients.TransferClient;
import com.deepthi.ecommerce.entity.Account;
import com.deepthi.ecommerce.entity.User;
import com.deepthi.ecommerce.exception.AccountNotFoundException;
import com.deepthi.ecommerce.service.UserService;

@RestController
@Transactional
public class AccountController 
{
	static Logger log = LoggerFactory.getLogger(AccountController.class.getName());
	
	@Autowired
	TransferClient transferClient;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/users/{id}/accounts")
	public ResponseEntity<String> addAccount(@RequestBody Account account,@PathVariable Long id) throws AccountNotFoundException
	{
		StringBuilder message=new StringBuilder();
		
		User user=new User();
		
		log.info("Getting user from database using userid");
		Optional<User> userById = userService.getUserById(id);
		if(userById.isPresent())
		{
			user=userById.get();
		}
		
		log.info("Checking whether the acno is available in FundsTransfer or not");
		ResponseEntity<String> checkAccount = transferClient.checkAccount(account.getAcno());
		
		if(checkAccount.getStatusCodeValue()==404)
		{
			log.warn("The acno doesn't found in FundsTransfer");
			throw new AccountNotFoundException("The account you want to add doesn't exist");
		}
		else
		{
			log.warn("Adding the acno to user");
			user.setAccount(account);
			userService.updateUser(user);
			message.append("Account has been added");
		}		
		
		return new ResponseEntity<>(message.toString(),HttpStatus.OK);
	}
	
}

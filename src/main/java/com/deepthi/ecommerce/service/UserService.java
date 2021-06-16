package com.deepthi.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepthi.ecommerce.entity.User;
import com.deepthi.ecommerce.repository.UserRepository;

@Service
public class UserService 
{

	@Autowired
	UserRepository userRepository;
	
	public User getUserByEmail(String email) 
	{
		return userRepository.findByEmail(email);
	}

	public User addUser(User user) 
	{
		return userRepository.save(user);
	}

	public Optional<User> getUserById(Long id) 
	{
		return userRepository.findById(id);
	}

	public User updateUser(User user) 
	{
		return userRepository.save(user);
	}
}

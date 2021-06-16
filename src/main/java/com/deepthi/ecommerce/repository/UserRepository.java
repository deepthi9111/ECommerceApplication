package com.deepthi.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepthi.ecommerce.entity.User;

public interface UserRepository extends JpaRepository<User, Long> 
{

	User findByEmail(String email);

}

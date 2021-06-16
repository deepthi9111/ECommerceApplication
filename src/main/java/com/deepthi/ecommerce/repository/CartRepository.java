package com.deepthi.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepthi.ecommerce.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	List<Cart> findByUserid(Long id);

}

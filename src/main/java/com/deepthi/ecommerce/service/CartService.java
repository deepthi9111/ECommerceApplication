package com.deepthi.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepthi.ecommerce.entity.Cart;
import com.deepthi.ecommerce.repository.CartRepository;

@Service
public class CartService 
{
	@Autowired
	CartRepository cartRepository;

	public Cart addToCart(Cart cart) 
	{
		return cartRepository.save(cart);
	}

	public List<Cart> getAllByUserId(Long id) 
	{
		return cartRepository.findByUserid(id);
	}

	public void deleteProductFromCart(Cart c) 
	{
		cartRepository.deleteById(c.getCartId());
		
	}
	
	
}

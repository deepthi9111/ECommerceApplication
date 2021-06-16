package com.deepthi.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.deepthi.ecommerce.entity.Cart;
import com.deepthi.ecommerce.repository.CartRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class CartServiceTest 
{
	@Mock
	CartRepository cartRepository;
	
	@InjectMocks
	CartService cartService;
	
	@Test
	void testAddToCart() 
	{
		Cart cart=new Cart();
		cart.setCartId(1L);
		cart.setPid(1L);
		cart.setUserid(1L);
		
		when(cartRepository.save(cart)).thenReturn(cart);
		
		Cart addToCart = cartService.addToCart(cart);
		
		assertTrue(cart.getCartId()==addToCart.getCartId());
	}

	@Test
	void testGetAllByUserId() 
	{
		List<Cart> cartList=new ArrayList<>();
		
		cartList.add(new Cart(1L,1L,1L));
		cartList.add(new Cart(2L,2L,2L));
		cartList.add(new Cart(3L,2L,1L));
		cartList.add(new Cart(4L,3L,2L));
		cartList.add(new Cart(5L,4L,1L));
		
		when(cartRepository.findByUserid(1L)).thenReturn(cartList.stream().filter(cart->cart.getUserid()==1L).collect(Collectors.toList()));
		
		List<Cart> allByUserId = cartService.getAllByUserId(1L);
		
		assertTrue(allByUserId.size()==3);
	}

	@Test
	void testDeleteProductFromCart() 
	{
		Cart cart=new Cart();
		cart.setCartId(1L);
		cart.setPid(1L);
		cart.setUserid(1L);
		
		doNothing().when(cartRepository).deleteById(cart.getCartId());
		
		cartService.deleteProductFromCart(cart);
		
		verify(cartRepository).deleteById(cart.getCartId());		
	}

}

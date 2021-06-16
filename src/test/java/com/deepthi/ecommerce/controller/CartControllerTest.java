package com.deepthi.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.deepthi.ecommerce.entity.Cart;
import com.deepthi.ecommerce.entity.Product;
import com.deepthi.ecommerce.exception.ProductAlreadyAddedException;
import com.deepthi.ecommerce.exception.ProductNotInCartException;
import com.deepthi.ecommerce.service.CartService;
import com.deepthi.ecommerce.service.ProductService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class CartControllerTest 
{
	@Mock
	CartService cartService;
	
	@Mock
	ProductService productService;
	
	@InjectMocks
	CartController cartController;
	
	@Test
	@DisplayName("Add To Cart : Positive Scenario")
	void testAddProductToCart() throws ProductAlreadyAddedException 
	{
		List<Cart> cartList=new ArrayList<>();
		
		Cart cart=new Cart(1L,1L,1L);
		
		when(cartService.getAllByUserId(1L)).thenReturn(cartList);
		when(cartService.addToCart(cart)).thenReturn(cart);
		
		ResponseEntity<String> addProductToCart = cartController.addProductToCart(1L, 1L);
		
		assertTrue(addProductToCart.getStatusCodeValue()==200);
	}
	
	@Test
	@DisplayName("Add To Cart : Negative Scenario")
	void testAddProductToCart2() throws ProductAlreadyAddedException 
	{
		List<Cart> cartList=new ArrayList<>();
		
		Cart cart=new Cart(1L,1L,1L);
		
		cartList.add(cart);
		
		when(cartService.getAllByUserId(1L)).thenReturn(cartList);
		when(cartService.addToCart(cart)).thenReturn(cart);
		
		ProductAlreadyAddedException e = assertThrows(ProductAlreadyAddedException.class, ()->cartController.addProductToCart(1L, 1L));
	
		assertEquals("Product has already added into the cart", e.getMessage());
	}

	@Test
	@DisplayName("Delete From Cart : Positive Scenario")
	void testDeleteProductFromCart() throws ProductNotInCartException 
	{
		List<Cart> cartList=new ArrayList<>();
		
		Cart cart=new Cart(1L,1L,1L);
		
		cartList.add(cart);
		
		when(cartService.getAllByUserId(1L)).thenReturn(cartList);
		doNothing().when(cartService).deleteProductFromCart(cart);
		
		ResponseEntity<String> deleteProductFromCart = cartController.deleteProductFromCart(1L, 1L);
		
		assertTrue(deleteProductFromCart.getStatusCodeValue()==200);
	}
	
	@Test
	@DisplayName("Delete From Cart : Negative Scenario")
	void testDeleteProductFromCart2()
	{
		List<Cart> cartList=new ArrayList<>();
		
		Cart cart=new Cart(1L,1L,1L);
		
		cartList.add(cart);
		
		when(cartService.getAllByUserId(1L)).thenReturn(cartList);
	
		ProductNotInCartException e = assertThrows(ProductNotInCartException.class, ()->cartController.deleteProductFromCart(1L, 2L));
		
		assertEquals("The product you wanna delete is not in the cart", e.getMessage());
	}

	@Test
	@DisplayName("Display Cart Products : Positive Scenario")
	void testDisplayProductsInCart() 
	{
		List<Cart> cartList=new ArrayList<>();
		
		Cart cart=new Cart(1L,1L,1L);
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In Stock");
		
		cartList.add(cart);
		
		when(cartService.getAllByUserId(1L)).thenReturn(cartList);
		when(productService.getProductById(product.getProductId())).thenReturn(Optional.of(product));
		
		ResponseEntity<String> displayProductsInCart = cartController.displayProductsInCart(1L);
		
		assertTrue(displayProductsInCart.getStatusCodeValue()==200);
	}
	
	@Test
	@DisplayName("Display Cart Products : Negative Scenario")
	void testDisplayProductsInCart2() 
	{
		List<Cart> cartList=new ArrayList<>();
		
		when(cartService.getAllByUserId(1L)).thenReturn(cartList);
		
		ResponseEntity<String> displayProductsInCart = cartController.displayProductsInCart(1L);
		
		assertTrue(displayProductsInCart.getBody().equals("Your cart is empty"));
	}

}

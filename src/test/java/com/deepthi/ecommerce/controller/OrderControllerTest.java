package com.deepthi.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.deepthi.ecommerce.clients.TransferClient;
import com.deepthi.ecommerce.entity.Account;
import com.deepthi.ecommerce.entity.Cart;
import com.deepthi.ecommerce.entity.Order;
import com.deepthi.ecommerce.entity.Product;
import com.deepthi.ecommerce.entity.User;
import com.deepthi.ecommerce.exception.BeneficiaryNotFoundException;
import com.deepthi.ecommerce.exception.InSufficientBalanceException;
import com.deepthi.ecommerce.exception.OrderCannotBePlacedException;
import com.deepthi.ecommerce.service.CartService;
import com.deepthi.ecommerce.service.OrderService;
import com.deepthi.ecommerce.service.ProductService;
import com.deepthi.ecommerce.service.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class OrderControllerTest 
{
	@Mock
	OrderService orderService;
	
	@Mock
	CartService cartService;
	
	@Mock
	UserService userService;
	
	@Mock
	ProductService productService;
	
	@Mock
	TransferClient transferClient;
	
	@InjectMocks
	OrderController orderController;
	
	
	@Test
	@DisplayName("Order Products : Negative Scenario1")
	void testOrderProducts() 
	{
		Account sender=new Account();
		sender.setAcno(1673190501L);
		sender.setIfsc("HDFC0000549");
		sender.setBranch("Bangalore");
		sender.setBank("HDFC");
		
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("7075725533");
		user.setAddress("Kavali");
		user.setAccount(sender);
		
		List<Cart> cartList=new ArrayList<>();
		
		when(userService.getUserById(user.getUserId())).thenReturn(Optional.of(user));
		when(cartService.getAllByUserId(user.getUserId())).thenReturn(cartList);
		
		OrderCannotBePlacedException e = assertThrows(OrderCannotBePlacedException.class, ()->orderController.orderProducts(user.getUserId()));
	
		assertEquals("Order cannot be placed as your cart is empty", e.getMessage());
	}
	
	@Test
	@DisplayName("Order Products : Negative Scenario2")
	void testOrderProducts2() 
	{
		Account sender=new Account();
		sender.setAcno(1673190501L);
		sender.setIfsc("HDFC0000549");
		sender.setBranch("Bangalore");
		sender.setBank("HDFC");
		
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("7075725533");
		user.setAddress("Kavali");
		user.setAccount(sender);
		
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,0,"In stock");
		
		List<Cart> cartList=new ArrayList<>();
		Cart cart=new Cart(1L,1L,1L);
		cartList.add(cart);
		
		when(userService.getUserById(user.getUserId())).thenReturn(Optional.of(user));
		when(cartService.getAllByUserId(user.getUserId())).thenReturn(cartList);
		when(productService.getProductById(cart.getPid())).thenReturn(Optional.of(product));
		
		OrderCannotBePlacedException e = assertThrows(OrderCannotBePlacedException.class, ()->orderController.orderProducts(user.getUserId()));
	
		assertEquals("Order cannot be placed as one of the products in your cart is out of stock", e.getMessage());
	}

	@Test
	@DisplayName("Order Products : Negative Scenario3")
	void testOrderProducts3() throws InSufficientBalanceException, BeneficiaryNotFoundException, OrderCannotBePlacedException 
	{
		Account sender=new Account();
		sender.setAcno(1673190501L);
		sender.setIfsc("HDFC0000549");
		sender.setBranch("Bangalore");
		sender.setBank("HDFC");
		
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("7075725533");
		user.setAddress("Kavali");
		user.setAccount(sender);
		
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In stock");
		
		List<Cart> cartList=new ArrayList<>();
		Cart cart=new Cart(1L,1L,1L);
		cartList.add(cart);
		
		ResponseEntity<String> response=new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		when(userService.getUserById(user.getUserId())).thenReturn(Optional.of(user));
		when(cartService.getAllByUserId(user.getUserId())).thenReturn(cartList);
		when(productService.getProductById(cart.getPid())).thenReturn(Optional.of(product));
		when(transferClient.transferFunds(sender.getAcno(),1673190500L,800.0)).thenReturn(response);
		
		ResponseEntity<String> orderProducts = orderController.orderProducts(1L);
		
		assertTrue(orderProducts.getStatusCodeValue()==500);
	}
	
	@Test
	@DisplayName("Order Products : Positive Scenario")
	void testOrderProducts4() throws OrderCannotBePlacedException, InSufficientBalanceException, BeneficiaryNotFoundException 
	{
		Account sender=new Account();
		sender.setAcno(1673190501L);
		sender.setIfsc("HDFC0000549");
		sender.setBranch("Bangalore");
		sender.setBank("HDFC");
		
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("7075725533");
		user.setAddress("Kavali");
		user.setAccount(sender);
		
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In stock");
		
		Order order=new Order();
		order.setOrderId(1L);
		order.setUser(user);
		order.setProduct(product);
		order.setOrderedTime(LocalDateTime.now());
		order.setExpected(LocalDateTime.now().plusDays(7));
		
		List<Cart> cartList=new ArrayList<>();
		Cart cart=new Cart(1L,1L,1L);
		cartList.add(cart);
		
		ResponseEntity<String> response=new ResponseEntity<>(HttpStatus.OK);
		
		when(userService.getUserById(user.getUserId())).thenReturn(Optional.of(user));
		when(cartService.getAllByUserId(user.getUserId())).thenReturn(cartList);
		when(productService.getProductById(cart.getPid())).thenReturn(Optional.of(product));
		when(transferClient.transferFunds(sender.getAcno(),1673190500L,800.0)).thenReturn(response);
		when(orderService.addOrder(product,user)).thenReturn(order);
		when(productService.updateProduct(product)).thenReturn(product);
		doNothing().when(cartService).deleteProductFromCart(cart);
		
		ResponseEntity<String> orderProducts = orderController.orderProducts(1L);
		
		assertTrue(orderProducts.getStatusCodeValue()==200);
		
	}

	@Test
	@DisplayName("Orders History : Positive Scenario")
	void testOrdersHistory() 
	{
		List<Order> ordersList=new ArrayList<>();
		
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("7075725533");
		user.setAddress("Kavali");
		
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In stock");
		
		Order order=new Order(1L,product,user,LocalDateTime.now(),LocalDateTime.now().plusDays(7),LocalDateTime.now().plusDays(7));
	
		ordersList.add(order);
		
		when(orderService.getAllOrdersById(user.getUserId())).thenReturn(ordersList);
		
		ResponseEntity<String> ordersHistory = orderController.ordersHistory(user.getUserId());
		
		assertTrue(ordersHistory.getStatusCodeValue()==200);
	}
	
	@Test
	@DisplayName("Orders History : Negative Scenario")
	void testOrdersHistory2() 
	{
		List<Order> ordersList=new ArrayList<>();
		
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("7075725533");
		user.setAddress("Kavali");
		
		when(orderService.getAllOrdersById(user.getUserId())).thenReturn(ordersList);
		
		ResponseEntity<String> ordersHistory = orderController.ordersHistory(user.getUserId());
		
		assertTrue(ordersHistory.getBody().equals("Orders haven't placed yet"));
	}
}

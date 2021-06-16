package com.deepthi.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.deepthi.ecommerce.entity.Order;
import com.deepthi.ecommerce.entity.Product;
import com.deepthi.ecommerce.entity.User;
import com.deepthi.ecommerce.repository.OrderRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class OrderServiceTest 
{
	@Mock
	OrderRepository orderRepository;
	
	@InjectMocks
	OrderService orderService;
	
	/*@Test
	void testAddOrder() 
	{
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("7075725533");
		user.setAddress("Kavali");
		
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In stock");
		
		Order order=new Order();
		
		order.setOrderId(1L);
		order.setProduct(product);
		order.setUser(user);
		order.setOrderedTime(LocalDateTime.now());
		order.setExpected(LocalDateTime.now().plusDays(7));
		
		when(orderRepository.save(order)).thenReturn(order);
		
		Order addOrder = orderService.addOrder(product,user);
		
		assertTrue(addOrder.getOrderId()==order.getOrderId());
	}*/

	@Test
	void testGetAllOrdersById() 
	{
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("7075725533");
		user.setAddress("Kavali");
		
		User user2=new User();
		user2.setUserId(2L);
		user2.setName("Avula Mounika Deepthi");
		user2.setEmail("amouni1998@gmail.com");
		user2.setPassword("mouni@1234");
		user2.setMobile("7075725533");
		user2.setAddress("Kavali");
		
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In stock");
		
		List<Order> orderList=new ArrayList<>();
		orderList.add(new Order(1L,product,user,LocalDateTime.now(),LocalDateTime.now().plusDays(7),LocalDateTime.now().plusDays(7)));
		orderList.add(new Order(2L,product,user,LocalDateTime.now(),LocalDateTime.now().plusDays(7),LocalDateTime.now().plusDays(7)));
		orderList.add(new Order(3L,product,user2,LocalDateTime.now(),LocalDateTime.now().plusDays(7),LocalDateTime.now().plusDays(7)));
		orderList.add(new Order(4L,product,user,LocalDateTime.now(),LocalDateTime.now().plusDays(7),LocalDateTime.now().plusDays(7)));
		
		when(orderRepository.findAll()).thenReturn(orderList);
		
		List<Order> allOrdersById = orderService.getAllOrdersById(1L);
		
		assertTrue(allOrdersById.size()==3);
	}

}

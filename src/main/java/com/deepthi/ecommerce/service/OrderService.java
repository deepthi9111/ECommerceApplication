package com.deepthi.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepthi.ecommerce.entity.Order;
import com.deepthi.ecommerce.entity.Product;
import com.deepthi.ecommerce.entity.User;
import com.deepthi.ecommerce.repository.OrderRepository;

@Service
public class OrderService 
{
	@Autowired
	OrderRepository orderRespository;

	public List<Order> getAllOrdersById(Long id)
	{
		List<Order> findAll = orderRespository.findAll();
		
		return findAll.stream().filter(order->order.getUser().getUserId()==id).collect(Collectors.toList());
	
	}

	public Order addOrder(Product product, User user) 
	{
		LocalDateTime time=LocalDateTime.now();
		Order order=new Order();
		order.setProduct(product);
		order.setUser(user);
		order.setOrderedTime(time);
		order.setExpected(time.plusHours(2));
		
		return orderRespository.save(order);
		
	}
}

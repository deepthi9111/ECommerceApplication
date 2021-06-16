package com.deepthi.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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


@RestController
public class OrderController 
{
	@Autowired
	OrderService orderService;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	TransferClient transferClient;
	
	@PostMapping("/users/{id}/products/orders")
	public ResponseEntity<String> orderProducts(@PathVariable Long id) throws OrderCannotBePlacedException
	, InSufficientBalanceException, BeneficiaryNotFoundException
	{
		StringBuilder message=new StringBuilder();
		
		List<Product> cartProducts=new ArrayList<>();
		
		Double amount=0.0;
		
		User user=new User();
		
		Optional<User> userById = userService.getUserById(id);
		if(userById.isPresent())
		{
			user=userById.get();
		}
		
		Account sender = user.getAccount();
		
		List<Cart> allByUserId = cartService.getAllByUserId(id);
		
		if(allByUserId.isEmpty())
		{
			throw new OrderCannotBePlacedException("Order cannot be placed as your cart is empty");
		}
		
		for(Cart c:allByUserId)
		{				
			Optional<Product> productById = productService.getProductById(c.getPid());
			if(productById.isPresent())
			{
				Product product=productById.get();
				if(product.getCount()<=0)
				{
					throw new OrderCannotBePlacedException("Order cannot be placed as one of the products in your cart is out of stock");
				}
				
				cartProducts.add(product);
				amount=amount+product.getPrice();
			}
		}
		
		ResponseEntity<String> transferFunds = transferClient.transferFunds(sender.getAcno(),1673190500L,amount);
		
		if(transferFunds.getStatusCodeValue()==200)
		{
			for(Product product:cartProducts)
			{			
				orderService.addOrder(product,user);
				
				product.setCount(product.getCount()-1);
				productService.updateProduct(product);
			}
			
			message.append("Order confirmed");
			
			for(Cart c:allByUserId)
			{
				cartService.deleteProductFromCart(c);
			}
			
			return new ResponseEntity<>(message.toString(),HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/users/{id}/orders")
	public ResponseEntity<String> ordersHistory(@PathVariable Long id)
	{
		StringBuilder message=new StringBuilder();
		
		List<Order> allOrdersById = orderService.getAllOrdersById(id);
		
		if(allOrdersById.isEmpty())
		{
			message.append("Orders haven't placed yet");
		}
		else
		{
			message.append("Your orders : \n\n");
			int sno=0;
			for(Order order:allOrdersById)
			{
				sno++;
				
				message.append(sno+". "+order.getOrderId()+"	"+order.getProduct().getName()+"	"+order.getOrderedTime()+"\n");
			}
		}
		
		int sno=0;
		for(Order order : allOrdersById)
		{
			sno++;
			message.append(sno+". Product : "+order.getProduct().toString()+"		Expected Delivery : "+order.getExpected()+"/n");
		}
		
		return new ResponseEntity<>(message.toString(),HttpStatus.OK);	
	}
}

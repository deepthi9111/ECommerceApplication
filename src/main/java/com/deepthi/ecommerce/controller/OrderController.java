package com.deepthi.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	static Logger log = LoggerFactory.getLogger(OrderController.class.getName());
	
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
	public ResponseEntity<String> orderProducts(@PathVariable Long id) throws OrderCannotBePlacedException, InSufficientBalanceException, BeneficiaryNotFoundException
	{
		StringBuilder message=new StringBuilder();
		
		List<Product> cartProducts=new ArrayList<>();
		
		Double amount=0.0;
		
		User user=new User();
		
		log.info("Getting the user from database using user id");
		Optional<User> userById = userService.getUserById(id);
		if(userById.isPresent())
		{
			user=userById.get();
		}
		
		log.info("Fetching the account from user");
		Account sender = user.getAccount();
		
		log.info("Getting all the products from the cart belongs to the user");
		List<Cart> allByUserId = cartService.getAllByUserId(id);
		
		log.info("Checking whether the cart is empty or not");
		if(allByUserId.isEmpty())
		{
			throw new OrderCannotBePlacedException("Order cannot be placed as your cart is empty");
		}
		
		for(Cart c:allByUserId)
		{				
			log.info("Getting the product using product id");
			Optional<Product> productById = productService.getProductById(c.getPid());
			
			if(productById.isPresent())
			{
				Product product=productById.get();
				
				log.info("Checking whether the product is in stock or not");
				if(product.getCount()<=0)
				{
					throw new OrderCannotBePlacedException("Order cannot be placed as one of the products in your cart is out of stock");
				}
				
				cartProducts.add(product);
				amount=amount+product.getPrice();
			}
		}
		
		log.info("Making the payment");
		ResponseEntity<String> transferFunds = transferClient.transferFunds(sender.getAcno(),1673190500L,amount);
		
		if(transferFunds.getStatusCodeValue()==200)
		{
			for(Product product:cartProducts)
			{			
				log.info("Order will placed as the payment is successfull");
				orderService.addOrder(product,user);
				
				product.setCount(product.getCount()-1);
				productService.updateProduct(product);
			}
			
			message.append("Order confirmed");
			
			log.info("Deleting the products from cart as we ordered those products");
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
		
		log.info("Getting all the orders belongs to the user");
		List<Order> allOrdersById = orderService.getAllOrdersById(id);
		
		log.info("Checkig whether the history of orders is empty or not");
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
		
		return new ResponseEntity<>(message.toString(),HttpStatus.OK);	
	}
}

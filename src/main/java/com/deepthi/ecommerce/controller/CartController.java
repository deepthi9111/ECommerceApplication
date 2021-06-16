package com.deepthi.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepthi.ecommerce.entity.Cart;
import com.deepthi.ecommerce.entity.Product;
import com.deepthi.ecommerce.entity.User;
import com.deepthi.ecommerce.exception.AccessRestrictedException;
import com.deepthi.ecommerce.exception.ProductAlreadyAddedException;
import com.deepthi.ecommerce.exception.ProductNotInCartException;
import com.deepthi.ecommerce.service.CartService;
import com.deepthi.ecommerce.service.ProductService;
import com.deepthi.ecommerce.service.UserService;

@RestController
public class CartController 
{
	static Logger log = LoggerFactory.getLogger(CartController.class.getName());
	
	@Autowired
	UserService userService;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/users/{id}/products/{pid}/carts")
	public ResponseEntity<String> addProductToCart(@PathVariable Long id, @PathVariable Long pid) throws ProductAlreadyAddedException, AccessRestrictedException
	{
		StringBuilder message=new StringBuilder();
		
		log.info("Getting the user from database using user id");
		Optional<User> userById = userService.getUserById(id);
		User user=new User();
		if(userById.isPresent())
		{
			user=userById.get();
		}
		
		log.info("Checking whether the user is admin or not");
		if(user.getEmail().equals("admin@ecommerce.co.in"))
		{
			log.info("Throwing an exception as the user is admin");
			throw new AccessRestrictedException("Admin cannot add the product into the cart");
		}
		Cart cart=new Cart();
		cart.setPid(pid);
		cart.setUserid(id);
		
		List<Cart> allByUserId = cartService.getAllByUserId(id);
		
		for(Cart c:allByUserId)
		{
			if(c.getPid()==pid)
			{
				log.warn("Product already added into the cart");
				throw new ProductAlreadyAddedException("Product has already added into the cart");
			}
		}
		log.info("Product is added into the cart");
		cartService.addToCart(cart);
		message.append("Product has been added to the cart successfully");
		
		return  new ResponseEntity<>(message.toString(),HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{id}/products/{pid}/carts")
	public ResponseEntity<String> deleteProductFromCart(@PathVariable Long id, @PathVariable Long pid) throws ProductNotInCartException
	{
		StringBuilder message=new StringBuilder();
		
		log.info("Getting all the products from cart belongs to the user");
		List<Cart> allByUserId = cartService.getAllByUserId(id);
		
		int notEqual=0;
		for(Cart c:allByUserId)
		{
			if(c.getPid()!=pid)
			{
				notEqual++;
			}
			else
			{
				log.info("Deleting the product from the cart");
				cartService.deleteProductFromCart(c);
				message.append("Product has been deleted from cart");
			}
		}
		
		
		if(notEqual==allByUserId.size())
		{
			log.warn("can't delete the product as the product is not in the cart");
			throw new ProductNotInCartException("The product you wanna delete is not in the cart");
		}
		
		return new ResponseEntity<>(message.toString(),HttpStatus.OK);
	}
	
	@GetMapping("/users/{id}/carts")
	public ResponseEntity<String> displayProductsInCart(@PathVariable Long id)
	{
		StringBuilder message=new StringBuilder();
		
		log.info("Getting all the products from cart belongs to the user");
		List<Cart> allByUserId = cartService.getAllByUserId(id);
		
		log.info("Checking whether the cart is empty or not");
		if(allByUserId.isEmpty())
		{
			message.append("Your cart is empty");
		}
		else
		{
			int sno=0;
			for(Cart c:allByUserId)
			{
				sno++;
				message.append(sno+". ");
				log.info("Getting the product using product id");
				Optional<Product> productById = productService.getProductById(c.getPid());
			
				if(productById.isPresent())
				{
					Product product=productById.get();
					message.append(product.getName()+" "+product.getCategory()+" "+product.getPrice()+"\n");
				}
			}
		}
		return new ResponseEntity<>(message.toString(),HttpStatus.OK);
	}
}

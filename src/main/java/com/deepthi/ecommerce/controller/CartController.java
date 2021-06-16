package com.deepthi.ecommerce.controller;

import java.util.List;
import java.util.Optional;

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
import com.deepthi.ecommerce.exception.ProductAlreadyAddedException;
import com.deepthi.ecommerce.exception.ProductNotInCartException;
import com.deepthi.ecommerce.service.CartService;
import com.deepthi.ecommerce.service.ProductService;

@RestController
public class CartController 
{
	@Autowired
	CartService cartService;
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/users/{id}/products/{pid}/carts")
	public ResponseEntity<String> addProductToCart(@PathVariable Long id, @PathVariable Long pid) throws ProductAlreadyAddedException
	{
		StringBuilder message=new StringBuilder();
		
		Cart cart=new Cart();
		cart.setPid(pid);
		cart.setUserid(id);
		
		List<Cart> allByUserId = cartService.getAllByUserId(id);
		
		for(Cart c:allByUserId)
		{
			if(c.getPid()==pid)
			{
				throw new ProductAlreadyAddedException("Product has already added into the cart");
			}
		}
		cartService.addToCart(cart);
		message.append("Product has been added to the cart successfully");
		
		return  new ResponseEntity<>(message.toString(),HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{id}/products/{pid}/carts")
	public ResponseEntity<String> deleteProductFromCart(@PathVariable Long id, @PathVariable Long pid) throws ProductNotInCartException
	{
		StringBuilder message=new StringBuilder();
		
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
				cartService.deleteProductFromCart(c);
				message.append("Product has been deleted from cart");
			}
		}
		
		if(notEqual==allByUserId.size())
		{
			throw new ProductNotInCartException("The product you wanna delete is not in the cart");
		}
		
		return new ResponseEntity<>(message.toString(),HttpStatus.OK);
	}
	
	@GetMapping("/users/{id}/carts")
	public ResponseEntity<String> displayProductsInCart(@PathVariable Long id)
	{
		StringBuilder message=new StringBuilder();
		
		List<Cart> allByUserId = cartService.getAllByUserId(id);
		
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

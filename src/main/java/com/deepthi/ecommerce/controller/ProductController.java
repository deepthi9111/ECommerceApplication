package com.deepthi.ecommerce.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deepthi.ecommerce.entity.Product;
import com.deepthi.ecommerce.entity.User;
import com.deepthi.ecommerce.exception.AccessRestrictedException;
import com.deepthi.ecommerce.service.ProductService;
import com.deepthi.ecommerce.service.UserService;

@RestController
public class ProductController 
{
	static Logger log = LoggerFactory.getLogger(ProductController.class.getName());
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/users/{id}/products")
	public ResponseEntity<String> addProduct(@RequestBody Product product,@PathVariable Long id) throws AccessRestrictedException
	{
		StringBuilder message=new StringBuilder();
		
		User user=new User();
		
		log.info("Getting user from database using user id");
		Optional<User> userById = userService.getUserById(id);
		if(userById.isPresent())
		{
			user = userById.get();
		}
		
		log.info("Checking whether the logged in user is admin or not");
		if(user.getEmail().equals("admin@ecommerce.co.in"))
		{
			log.info("Checking whether the product is already in database or not");
			Product productByName = productService.getProductByName(product.getName());
			
			if(productByName!=null)
			{
				log.info("Updating the count of products if the product has already been in database");
				productByName.setCount(productByName.getCount()+product.getCount());
				productService.saveProduct(productByName);
				message.append("Product has been updated successfully");
			}
			else
			{
				log.info("Adding new product into the database if the product is not there in database");
				message.append("product has been added successfully");
				productService.saveProduct(product);
			}
		}
		else
		{
			log.warn("No one can add new product into the database other than admin");
			throw new AccessRestrictedException("You don't have privilege to access the resources");
		}
		return new ResponseEntity<>(message.toString(),HttpStatus.OK);
	}
	
	@PutMapping("/users/{id}/products/{pid}")
	public ResponseEntity<String> editProduct(@RequestBody Product product, @PathVariable Long id, @PathVariable Long pid) throws AccessRestrictedException
	{
		StringBuilder message=new StringBuilder();
		
		User user=new User();
		
		log.info("Getting user from database using user id");
		Optional<User> userById = userService.getUserById(id);
		if(userById.isPresent())
		{
			user = userById.get();
		}
		
		log.info("Checking whether the logged in user is admin or not");
		if(user.getEmail().equals("admin@ecommerce.co.in"))
		{
			Product savedProduct=new Product();
			
			log.info("Getting the product from databse using product id");
			Optional<Product> productById = productService.getProductById(pid);
			if(productById.isPresent())
			{
				savedProduct=productById.get();
			}
			
			log.info("Updating the product details to the existing product");
			savedProduct.setCount(product.getCount());
			savedProduct.setPrice(product.getPrice());
			savedProduct.setStatus(product.getStatus());
			
			message.append("product has been edited successfully");
			productService.saveProduct(savedProduct);
		}
		else
		{
			throw new AccessRestrictedException("You don't have privilege to access the resources");
		}
		return new ResponseEntity<>(message.toString(),HttpStatus.OK);
	}
	
	
	@GetMapping("/products/{category}")
	public ResponseEntity<String> searchProductByCategory(@PathVariable String category)
	{
		StringBuilder message=new StringBuilder();
		
		List<Product> allProductsByCategory = productService.getAllProductsByCategory(category);
		
		if(allProductsByCategory.isEmpty())
		{
			message.append("No products");
		}
		else
		{
			int sno=0;
			
			for(Product product:allProductsByCategory)
			{
				sno++;
				message.append(sno+". "+product.toString()+"\n");
			}
		}
		
		return new ResponseEntity<>(message.toString(),HttpStatus.OK);
	}
	
	
}

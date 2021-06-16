package com.deepthi.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

import com.deepthi.ecommerce.entity.Product;
import com.deepthi.ecommerce.entity.User;
import com.deepthi.ecommerce.exception.AccessRestrictedException;
import com.deepthi.ecommerce.service.ProductService;
import com.deepthi.ecommerce.service.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class ProductControllerTest 
{
	@Mock
	ProductService productService;
	
	@Mock
	UserService userService;
	
	@InjectMocks
	ProductController productController;
	
	@Test
	@DisplayName("Add Product : Positive Scenario")
	void testAddProduct() throws AccessRestrictedException 
	{	
		User user=new User();
		user.setUserId(1L);
		user.setName("Ecommerce");
		user.setEmail("admin@ecommerce.co.in");
		user.setPassword("ecommerce@1234");
		user.setMobile("1800267851");
		user.setAddress("Bangalore Headquarters");
		
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In Stock");
		
		when(userService.getUserById(user.getUserId())).thenReturn(Optional.of(user));
		when(productService.getProductByName(product.getName())).thenReturn(null);
		when(productService.saveProduct(product)).thenReturn(product);
		
		ResponseEntity<String> addProduct = productController.addProduct(product, user.getUserId());
		
		assertTrue(addProduct.getStatusCodeValue()==200);
	}
	
	@Test
	@DisplayName("Add Product : Positive Scenario2")
	void testAddProduct2() throws AccessRestrictedException 
	{	
		User user=new User();
		user.setUserId(1L);
		user.setName("Ecommerce");
		user.setEmail("admin@ecommerce.co.in");
		user.setPassword("ecommerce@1234");
		user.setMobile("1800267851");
		user.setAddress("Bangalore Headquarters");
		
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In Stock");
		
		when(userService.getUserById(user.getUserId())).thenReturn(Optional.of(user));
		when(productService.getProductByName(product.getName())).thenReturn(product);
		when(productService.saveProduct(product)).thenReturn(product);
		
		productController.addProduct(product, user.getUserId());
		
		assertTrue(product.getCount()==20);
	}
	
	@Test
	@DisplayName("Add Product : Negative Scenario")
	void testAddProduct3() 
	{
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("7075725533");
		user.setAddress("Kavali");
		
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In Stock");
		
		when(userService.getUserById(user.getUserId())).thenReturn(Optional.of(user));
		
		AccessRestrictedException e = assertThrows(AccessRestrictedException.class, ()->productController.addProduct(product, user.getUserId()));
		
		assertEquals("You don't have privilege to access the resources", e.getMessage());
	}

	@Test
	@DisplayName("Edit Product : Positive Scenario")
	void testEditProduct() throws AccessRestrictedException 
	{
		User user=new User();
		user.setUserId(1L);
		user.setName("Ecommerce");
		user.setEmail("admin@ecommerce.co.in");
		user.setPassword("ecommerce@1234");
		user.setMobile("1800267851");
		user.setAddress("Bangalore Headquarters");
		
		Product oldProduct=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In Stock");
		Product newProduct=new Product(1L,"Harvard","Sweatshirt",900.0,15,"In Stock");
		
		when(userService.getUserById(user.getUserId())).thenReturn(Optional.of(user));
		when(productService.getProductById(newProduct.getProductId())).thenReturn(Optional.of(oldProduct));
		when(productService.saveProduct(oldProduct)).thenReturn(oldProduct);
		
		productController.editProduct(newProduct, user.getUserId(), oldProduct.getProductId());
		
		assertTrue(oldProduct.getCount()==15);
	}
	
	@Test
	@DisplayName("Edit Product : Negative Scenario")
	void testEditProduct2() 
	{
		User user=new User();
		user.setUserId(1L);
		user.setName("Avula Mounika Deepthi");
		user.setEmail("amouni1998@gmail.com");
		user.setPassword("mouni@1234");
		user.setMobile("7075725533");
		user.setAddress("Kavali");
		
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In Stock");
		
		when(userService.getUserById(user.getUserId())).thenReturn(Optional.of(user));
		
		AccessRestrictedException e = assertThrows(AccessRestrictedException.class, ()->productController.editProduct(product, user.getUserId(),product.getProductId()));
	
		assertEquals("You don't have privilege to access the resources", e.getMessage());
	}

	@Test
	void testSearchProductByCategory() 
	{
		List<Product> productsList=new ArrayList<>();
		
		productsList.add(new Product(1L,"Harvard","Sweatshirt",800.0,10,"In stock"));
		productsList.add(new Product(2L,"Dressberry","Sweatshirt",800.0,10,"In stock"));
		productsList.add(new Product(3L,"Macbook","Laptop",90000.0,10,"In stock"));
		productsList.add(new Product(4L,"Zenboook","Laptop",80000.0,10,"In stock"));
		
		when(productService.getAllProductsByCategory("Laptop")).thenReturn(productsList.stream().filter(product->product.getCategory().equals("Laptop")).collect(Collectors.toList()));
	
		ResponseEntity<String> searchProductByCategory = productController.searchProductByCategory("Laptop");
		
		assertTrue(searchProductByCategory.getStatusCodeValue()==200);
	}

}

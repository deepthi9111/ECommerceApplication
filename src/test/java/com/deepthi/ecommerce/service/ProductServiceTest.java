package com.deepthi.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.deepthi.ecommerce.entity.Product;
import com.deepthi.ecommerce.repository.ProductRepository;


@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class ProductServiceTest 
{
	@Mock
	ProductRepository productRepository;
	
	@InjectMocks
	ProductService productService;

	@Test
	void testGetProductByName() 
	{
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In stock");
		
		when(productRepository.findByName(product.getName())).thenReturn(product);
		
		Product productByName = productService.getProductByName(product.getName());
		
		assertTrue(productByName.getProductId()==product.getProductId());
	}

	@Test
	void testSaveProduct() 
	{
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In stock");
		
		when(productRepository.save(product)).thenReturn(product);
		
		Product saveProduct = productService.saveProduct(product);
		
		assertTrue(saveProduct.getProductId()==product.getProductId());
	}

	@Test
	void testUpdateProduct() 
	{
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In stock");
		
		when(productRepository.save(product)).thenReturn(product);
		
		Product updateProduct = productService.updateProduct(product);
		
		assertTrue(updateProduct.getProductId()==product.getProductId());
	}

	@Test
	void testGetProductById() 
	{
		Product product=new Product(1L,"Harvard","Sweatshirt",800.0,10,"In stock");
		
		when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));
		
		Optional<Product> productById = productService.getProductById(product.getProductId());
		
		assertTrue(productById.isPresent());
	}

	@Test
	void testGetAllProductsByCategory() 
	{
		List<Product> productsList=new ArrayList<>();
		
		productsList.add(new Product(1L,"Harvard","Sweatshirt",800.0,10,"In stock"));
		productsList.add(new Product(2L,"Dressberry","Sweatshirt",800.0,10,"In stock"));
		productsList.add(new Product(3L,"Macbook","Laptop",90000.0,10,"In stock"));
		productsList.add(new Product(4L,"Zenboook","Laptop",80000.0,10,"In stock"));
		
		when(productRepository.findByCategory("Laptop")).thenReturn(productsList.stream().filter(product->product.getCategory().equals("Laptop")).collect(Collectors.toList()));
		
		List<Product> allProductsByCategory = productService.getAllProductsByCategory("Laptop");
		
		assertTrue(allProductsByCategory.size()==2);
	}

}

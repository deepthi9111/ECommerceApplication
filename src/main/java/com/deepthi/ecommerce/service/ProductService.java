package com.deepthi.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepthi.ecommerce.entity.Product;
import com.deepthi.ecommerce.repository.ProductRepository;

@Service
public class ProductService 
{
	@Autowired
	ProductRepository productRepository;
	
	public Product getProductByName(String name) 
	{
		return productRepository.findByName(name);
	}

	public Product saveProduct(Product product) 
	{
		return productRepository.save(product);
	}
	
	public Product updateProduct(Product product) 
	{
		return productRepository.save(product);
	}

	public Optional<Product> getProductById(Long pid) 
	{
		return productRepository.findById(pid);
	}
	
	public List<Product> getAllProductsByCategory(String category)
	{
		return productRepository.findByCategory(category);
	}
}

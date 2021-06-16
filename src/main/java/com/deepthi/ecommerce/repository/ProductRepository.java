package com.deepthi.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepthi.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>
{

	Product findByName(String name);
	
	List<Product> findByCategory(String category);

}

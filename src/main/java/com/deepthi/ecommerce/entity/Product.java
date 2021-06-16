package com.deepthi.ecommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "tb_product")
@SequenceGenerator(name="product_generator", initialValue=1, allocationSize=1)
public class Product 
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="product_generator")
	@Column(name = "pid")
	private Long productId;
	
	@Column
	private String name;
	
	@Column
	private String category;
	
	@Column
	private Double price;
	
	@Column
	private Integer count;
	
	@Column
	private String status;

	public Product(Long productId, String name, String category, Double price, Integer count, String status) {
		super();
		this.productId = productId;
		this.name = name;
		this.category = category;
		this.price = price;
		this.count = count;
		this.status = status;
	}

	public Product() 
	{
		super();
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", name=" + name + ", category=" + category + ", price=" + price
				+ ", count=" + count + ", status=" + status + "]";
	}
	
	
	
}

package com.deepthi.ecommerce.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_user_order")
@SequenceGenerator(name="order_generator", initialValue=1, allocationSize=1)
public class Order 
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="order_generator")
	@Column(name = "oid")
	private Long orderId;
	
	@OneToOne
	private Product product;
	
	@ManyToOne
	private User user;
	
	@Column
	private LocalDateTime orderedTime;
	
	@Column
	private LocalDateTime expected;
	
	@Column
	private LocalDateTime delivered;

	public Order(Long orderId, Product product, User user, LocalDateTime orderedTime, LocalDateTime expected,
			LocalDateTime delivered) 
	{
		super();
		this.orderId = orderId;
		this.product = product;
		this.user = user;
		this.orderedTime = orderedTime;
		this.expected = expected;
		this.delivered = delivered;
	}

	public Order() 
	{
		super();
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getOrderedTime() {
		return orderedTime;
	}

	public void setOrderedTime(LocalDateTime orderedTime) {
		this.orderedTime = orderedTime;
	}

	public LocalDateTime getExpected() {
		return expected;
	}

	public void setExpected(LocalDateTime expected) {
		this.expected = expected;
	}

	public LocalDateTime getDelivered() {
		return delivered;
	}

	public void setDelivered(LocalDateTime delivered) {
		this.delivered = delivered;
	}
	
}

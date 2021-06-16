package com.deepthi.ecommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_user_cart")
@SequenceGenerator(name="cart_generator", initialValue=1, allocationSize=1)
public class Cart 
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cart_generator")
	@Column(name = "cid")
	private Long cartId;
	
	@Column
	private Long pid;
	
	@Column
	private Long userid;

	public Cart(Long cartId, Long pid, Long userid) {
		super();
		this.cartId = cartId;
		this.pid = pid;
		this.userid = userid;
	}

	public Cart() {
		super();
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", pid=" + pid + ", userid=" + userid + "]";
	}

}

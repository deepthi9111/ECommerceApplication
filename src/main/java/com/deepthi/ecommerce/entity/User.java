package com.deepthi.ecommerce.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_user")
@SequenceGenerator(name="user_generator", initialValue=1, allocationSize=1)
public class User 
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_generator")
	@Column(name = "uid")
	private Long userId;
	
	@Column
	private String name;
	
	@Column
	@Email
	private String email;
	
	@Column
	@Size(min = 6, max=15,message = "Password length should be >=6 and <=15")
	private String password;
	
	@Column
	@Size(min=10, max = 10,message = "Mobile number should be of length 10")
	private String mobile;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Account account;
	
	@OneToMany
	private List<Order> orders;
	
	@Column
	private String address;

	public User(Long userId, String name, @Email String email,
			@Size(min = 6, max = 15, message = "Password length should be >=6 and <=15") String password,
			@Size(min = 10, max = 10, message = "Mobile number should be of length 10") String mobile,String address) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.address=address;
	}

	public User() 
	{
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
		
}

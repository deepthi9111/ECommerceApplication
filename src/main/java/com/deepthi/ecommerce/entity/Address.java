package com.deepthi.ecommerce.entity;

public class Address 
{
	private String doorNo;
	private String street;
	private String city;
	private String pincode;
	
	public Address(String doorNo, String street, String city, String pincode) {
		super();
		this.doorNo = doorNo;
		this.street = street;
		this.city = city;
		this.pincode = pincode;
	}

	public Address() 
	{
		super();
	}

	@Override
	public String toString() {
		return "Address : "+doorNo+", "+street+", "+city+", "+pincode;
	}
	
}

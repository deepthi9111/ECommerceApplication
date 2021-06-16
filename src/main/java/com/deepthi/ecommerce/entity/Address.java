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

	public String getDoorNo() {
		return doorNo;
	}

	public void setDoorNo(String doorNo) {
		this.doorNo = doorNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		return "Address : "+doorNo+", "+street+", "+city+", "+pincode;
	}
	
}

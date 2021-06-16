package com.deepthi.ecommerce.exception;

public class ProductNotInCartException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductNotInCartException(String message) 
	{
		super(message);
	}
	
}

package com.deepthi.ecommerce.exception;

public class ProductAlreadyAddedException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductAlreadyAddedException(String message) 
	{
		super(message);
	}
	
}

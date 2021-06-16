package com.deepthi.ecommerce.exception;

import java.util.List;

public class ErrorResponse 
{
	private Long errorCode;
	private List<String> details;
	
	public ErrorResponse(Long errorCode, List<String> details) 
	{
		super();
		this.errorCode = errorCode;
		this.details = details;
	}

	public ErrorResponse() 
	{
		super();
	}

	public Long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Long errorCode) {
		this.errorCode = errorCode;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "ErrorResponse [errorCode=" + errorCode + ", details=" + details + "]";
	}
}

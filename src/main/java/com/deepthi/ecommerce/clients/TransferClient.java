package com.deepthi.ecommerce.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import com.deepthi.ecommerce.exception.BeneficiaryNotFoundException;
import com.deepthi.ecommerce.exception.InSufficientBalanceException;

@FeignClient(name = "http://EUREKA-FUNDSTRANSFER-SERVICE")
public interface TransferClient 
{

	@PutMapping("/customers/funds/{facno}/{tacno}/{amount}")
	public ResponseEntity<String> transferFunds(@PathVariable Long facno, @PathVariable Long tacno, @PathVariable Double amount) 
			throws InSufficientBalanceException, 
				   BeneficiaryNotFoundException;
	
	@GetMapping("/customers/{acno}")
	public ResponseEntity<String> checkAccount(@PathVariable Long acno);
	
}

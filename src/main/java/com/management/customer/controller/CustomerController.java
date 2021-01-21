package com.management.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.management.customer.dto.CustomerDto;
import com.management.customer.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/saveCustomer")
	public ResponseEntity<String> saveCustomer(@RequestBody CustomerDto c)
	{
		return customerService.saveCustomer(c);
	}
	
}

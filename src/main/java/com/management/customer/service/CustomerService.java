package com.management.customer.service;

import org.springframework.http.ResponseEntity;

import com.management.customer.dto.CustomerDto;

public interface CustomerService {
public ResponseEntity<String> saveCustomer(CustomerDto c);
}

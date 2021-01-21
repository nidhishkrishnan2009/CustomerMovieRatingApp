package com.management.customer.serviceimpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.management.customer.dto.CustomerDto;
import com.management.customer.model.Customer;
import com.management.customer.repository.CustomerRepository;
import com.management.customer.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository repository;
	@Override
	public ResponseEntity<String> saveCustomer(CustomerDto c) {
	
		try {
			Customer customerModel=new Customer();
			BeanUtils.copyProperties(c, customerModel);
			repository.save(customerModel);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}

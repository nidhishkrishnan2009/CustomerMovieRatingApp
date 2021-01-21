package com.management.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.management.customer.service.MoviesService;

@RestController
@RequestMapping("/customer")
public class MoviesController {

	@Autowired
	private MoviesService movieService;
	
	@PutMapping("/{customerId:.+}/rate/{rating:.+}/movie/{movieName}")
	public ResponseEntity<String> saveMovies(@PathVariable long customerId,@PathVariable short rating,@PathVariable String movieName )
	{
		return movieService.saveMovies(customerId,rating,movieName);
	}
	
	
	@GetMapping("/findHighestRatedMovie")
	public ResponseEntity<?> findHighestRatedMovie()
	{
		return movieService.findHighestRatedMovie();
		
	}
	
	@GetMapping("/findHighestRatedCustomer")
	public ResponseEntity<?> findHighestRatedCustomer()
	{
		return movieService.findHighestRatedCustomer();
		
	}
}

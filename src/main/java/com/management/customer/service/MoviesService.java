package com.management.customer.service;

import org.springframework.http.ResponseEntity;

public interface MoviesService {
public ResponseEntity<String> saveMovies(long customerId,short rating,String movieName);
public ResponseEntity<?>findHighestRatedMovie();
public ResponseEntity<?>findHighestRatedCustomer();
}

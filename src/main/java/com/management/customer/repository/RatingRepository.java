package com.management.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.customer.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Short>{

}

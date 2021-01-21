package com.management.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.customer.model.Movies;

@Repository
public interface MovieRepository extends JpaRepository<Movies, Long>{

}

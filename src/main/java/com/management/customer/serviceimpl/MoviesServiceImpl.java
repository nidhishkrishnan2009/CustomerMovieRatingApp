package com.management.customer.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.management.customer.dto.AverageRatingDTO;
import com.management.customer.model.Customer;
import com.management.customer.model.Movies;
import com.management.customer.model.Rating;
import com.management.customer.repository.CustomerRepository;
import com.management.customer.repository.MovieRepository;
import com.management.customer.repository.RatingRepository;
import com.management.customer.service.MoviesService;

@Service
public class MoviesServiceImpl implements MoviesService {

	private static final double SCALE = Math.pow(10, 2);
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public ResponseEntity<String> saveMovies(long customerId,short rating,String movieName) {
		try {
			Movies movieModel=new Movies();
			Optional<Customer> c=customerRepository.findById(customerId);
			c.ifPresentOrElse(d->movieModel.setCustomer(d), ()->{
				throw new RuntimeException("Invalid customer id");
			});
			ratingRepository.findById(rating).ifPresentOrElse(r->movieModel.setRating(r), ()->{
				throw new RuntimeException("Invalid rating");
			});
			
			movieModel.setMovieName(movieName);
			movieRepository.save(movieModel);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			//TODO: Log the exception.
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> findHighestRatedMovie() {
		ResponseEntity<?> response = null;
		try {
			List<Movies> movieList = movieRepository.findAll();
			Map<Object, Object> movieAvg = movieList.stream()
					.collect(Collectors.groupingBy(Movies::getMovieName,
							Collectors.averagingInt(m -> m.getRating().getId())))
					.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.collect(Collectors.toMap(Map.Entry::getKey,
							v -> (Math.round(v.getValue().doubleValue() * SCALE)) / SCALE,
							(oldValue, newValues) -> oldValue, LinkedHashMap::new));
			response = movieAvg.isEmpty()
					? new ResponseEntity<>("Insufficient records for computation", HttpStatus.NO_CONTENT)
					: ResponseEntity.ok(movieAvg.entrySet().stream().findFirst().get());
		} catch (Exception e) {
			response = new ResponseEntity<>("Insufficient records for computation", HttpStatus.NO_CONTENT);
		}
		return response;
	}

	@Override
	public ResponseEntity<?> findHighestRatedCustomer() {
		try {
			List<Customer> customerList = customerRepository.findAll();

			Map<Customer, List<Rating>> movieGrouping = customerList.stream().collect(Collectors.toMap(c -> c,
					c -> c.getMovies().stream().map(Movies::getRating).collect(Collectors.toList())));

			double averageRating = (Math.round(movieGrouping.values().stream().flatMap(r -> r.stream())
					.collect(Collectors.averagingDouble(Rating::getId)) * SCALE)) / SCALE;

			List<AverageRatingDTO> customerRatings = new ArrayList<AverageRatingDTO>();

			movieGrouping.entrySet().stream().forEachOrdered(m -> {
				AverageRatingDTO avgDTO = new AverageRatingDTO();
				Customer c = m.getKey();
				double avgRating = (Math
						.round(m.getValue().stream().collect(Collectors.averagingDouble(Rating::getId)) * SCALE))
						/ SCALE;
				BeanUtils.copyProperties(c, avgDTO);
				avgDTO.setCustomerAverageRating(avgRating);
				avgDTO.setAverageRating(averageRating);
				customerRatings.add(avgDTO);
			});

			return ResponseEntity
					.ok(customerRatings.stream().max(Comparator.comparing(v -> v.getCustomerAverageRating())).get());

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}

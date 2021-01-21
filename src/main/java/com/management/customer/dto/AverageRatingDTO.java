package com.management.customer.dto;

public class AverageRatingDTO {

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public double getCustomerAverageRating() {
		return customerAverageRating;
	}
	public void setCustomerAverageRating(double customerAverageRating) {
		this.customerAverageRating = customerAverageRating;
	}
	public double getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
	private Long id;
	private String firstName;
	private String lastName;
	private double customerAverageRating;
	private double averageRating;
}

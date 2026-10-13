package com.example.demo.entities;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name="ratings")
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int ratingid;
	
	@Column
	int rating;
	
	@Column
	String comment;
	
	
	@OneToOne
	@JoinColumn(name="customerid") 
	Customer customer;
	
	@ManyToOne
	@JoinColumn(name="servicecenterid") 
	ServiceCenter serviceCenter;

	public Rating() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Rating(int rating, String comment, Customer customer, ServiceCenter serviceCenter) {
		super();
		this.rating = rating;
		this.comment = comment;
		this.customer = customer;
		this.serviceCenter = serviceCenter;
	}

	public int getRatingid() {
		return ratingid;
	}

	public void setRatingid(int ratingid) {
		this.ratingid = ratingid;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ServiceCenter getServiceCenter() {
		return serviceCenter;
	}

	public void setServiceCenter(ServiceCenter serviceCenter) {
		this.serviceCenter = serviceCenter;
	}
		
}

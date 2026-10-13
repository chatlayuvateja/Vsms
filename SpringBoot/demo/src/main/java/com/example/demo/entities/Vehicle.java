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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="vehicles")
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int vehicleid;
	
	@Column
	String vehiclenumber,model,fueltype;
	int registrationyear;
	
	
	@JoinColumn(name="brandid")
	@ManyToOne
	Brand brandid;
	
	@JoinColumn(name = "customerid")
	@ManyToOne
    Customer customerid;

	public Vehicle() {
		super();
	}

	public Vehicle(String vehiclenumber, String model, String fueltype, int registrationyear,
			Brand brandid, Customer customerid) {
		super();
		this.vehiclenumber = vehiclenumber;
		this.model = model;
		this.fueltype = fueltype;
		this.registrationyear = registrationyear;
		this.brandid = brandid;
		this.customerid = customerid;
	}

	public int getVehicleid() {
		return vehicleid;
	}

	public void setVehicleid(int vehicleid) {
		this.vehicleid = vehicleid;
	}

	public String getVehiclenumber() {
		return vehiclenumber;
	}

	public void setVehiclenumber(String vehiclenumber) {
		this.vehiclenumber = vehiclenumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFueltype() {
		return fueltype;
	}

	public void setFueltype(String fueltype) {
		this.fueltype = fueltype;
	}

	public int getRegistrationyear() {
		return registrationyear;
	}

	public void setRegistrationyear(int registrationyear) {
		this.registrationyear = registrationyear;
	}

	public Brand getBrandid() {
		return brandid;
	}

	public void setBrandid(Brand brandid) {
		this.brandid = brandid;
	}

	public Customer getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Customer customerid) {
		this.customerid = customerid;
	}

}

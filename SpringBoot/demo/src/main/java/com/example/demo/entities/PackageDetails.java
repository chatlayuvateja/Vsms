package com.example.demo.entities;


import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="packagedetails")
public class PackageDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int packagedetailsid;
	
	@Column
	String packagename;
	
	@NumberFormat(pattern = "#00000.00#")
	double cost;
	
	@JsonIgnoreProperties("login")
	@JoinColumn(name="servicecenterid")
	@ManyToOne
	ServiceCenter servicecenter;
	
	@JsonIgnoreProperties("packagedetails")
	@ManyToMany(cascade = {CascadeType.MERGE})
	@JoinTable(name="packages",
	joinColumns=@JoinColumn(name="packageid"),inverseJoinColumns=@JoinColumn(name="facilityid"))
	Set<Facility> facilities;

	public PackageDetails() {
		super();
		// TODO Auto-generated constructor stub
	}


	public PackageDetails(String packagename) {
		super();
		this.packagename = packagename;
	}

	public PackageDetails(int packagedetailsid) {
		super();
		this.packagedetailsid = packagedetailsid;
	}

	public PackageDetails(String packagename, double cost, ServiceCenter servicecenter,
			Set<Facility> facilities) {
		super();
		this.packagename = packagename;
		this.cost = cost;
		this.servicecenter = servicecenter;
		this.facilities = facilities;
	}

	public int getPackagedetailsid() {
		return packagedetailsid;
	}

	public void setPackagedetailsid(int packagedetailsid) {
		this.packagedetailsid = packagedetailsid;
	}

	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public ServiceCenter getServicecenter() {
		return servicecenter;
	}

	public void setServicecenter(ServiceCenter servicecenter) {
		this.servicecenter = servicecenter;
	}

	public Set<Facility> getFacilities() {
		return facilities;
	}

	public void setFacilities(Set<Facility> facilities) {
		this.facilities = facilities;
	}
		
}

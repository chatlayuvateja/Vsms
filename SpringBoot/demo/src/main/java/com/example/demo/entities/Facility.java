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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="facilities")
public class Facility {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int facilityid;
	
	@Column
	String facilityname;
	
	
	@ManyToMany(cascade = {CascadeType.MERGE})
	@JoinTable(name="packages",
	joinColumns=@JoinColumn(name="facilityid"),inverseJoinColumns=@JoinColumn(name="packageid"))
	Set<PackageDetails> packagedetails;

	
	public Facility() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Facility(int facilityid, String facilityname, Set<PackageDetails> packagedetails) {
		super();
		this.facilityid = facilityid;
		this.facilityname = facilityname;
		this.packagedetails = packagedetails;
	}

	public Facility(int facilityid) {
		super();
		this.facilityid = facilityid;
	}

	public int getFacilityid() {
		return facilityid;
	}

	public void setFacilityid(int facilityid) {
		this.facilityid = facilityid;
	}

	public String getFacilityname() {
		return facilityname;
	}

	public void setFacilityname(String facilityname) {
		this.facilityname = facilityname;
	}

	public Set<PackageDetails> getPackagedetails() {
		return packagedetails;
	}

	public void setPackagedetails(Set<PackageDetails> packagedetails) {
		this.packagedetails = packagedetails;
	}
	
}

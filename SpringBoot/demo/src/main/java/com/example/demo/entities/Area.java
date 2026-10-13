package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="area")
public class Area 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int areaid;
	@Column
	String areaname,pincode;
	
	@OneToOne
	@JoinColumn(name="cityid")
	City city;

	public Area() {
		super();
	}

	public Area(String areaid, String areaname, String pincode, City city) {
		super();
		int areasid=Integer.parseInt(areaid);
		this.areaid = areasid;
		this.areaname = areaname;
		this.pincode = pincode;
		this.city = city;
	}

	public int getAreaid() {
		return areaid;
	}

	public void setAreaid(int areaid) {
		this.areaid = areaid;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
		
}

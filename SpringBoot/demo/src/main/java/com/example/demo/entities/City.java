package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="city")
public class City {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int cityid;
	
	@Column
	String cityname;

	public City() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public City(int cityid, String cityname) {
		super();
		this.cityid = cityid;
		this.cityname = cityname;
	}


	public int getCityid() {
		return cityid;
	}


	public void setCityid(int cityid) {
		this.cityid = cityid;
	}


	public String getCityname() {
		return cityname;
	}


	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	
	
}

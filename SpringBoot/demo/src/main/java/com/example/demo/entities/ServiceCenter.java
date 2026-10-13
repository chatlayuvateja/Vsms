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
@Table(name="servicecenters")
public class ServiceCenter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int servicecenterid;
	
	@Column
	String scname,emailid,contactno,lane;
	@Column
	int bookinglimit;
	
	
	
	@OneToOne
	@JoinColumn(name="loginid")
	Login login;
	
	@OneToOne
	@JoinColumn(name="brandid")
	Brand brand;
	
	@OneToOne
	@JoinColumn(name="areaid")
	Area area;
	

	public ServiceCenter() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ServiceCenter(String scname, String emailid, String contactno, String lane, int bookinglimit, Login login,
			Brand brand, Area area) {
		super();
		this.scname = scname;
		this.emailid = emailid;
		this.contactno = contactno;
		this.lane = lane;
		this.bookinglimit = bookinglimit;
		this.login = login;
		this.brand = brand;
		this.area = area;
	
	}

	public int getServicecenterid() {
		return servicecenterid;
	}

	public void setServicecenterid(int serviecenterid) {
		this.servicecenterid = serviecenterid;
	}

	public String getScname() {
		return scname;
	}

	public void setScname(String scname) {
		this.scname = scname;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getContactno() {
		return contactno;
	}

	public void setContactno(String contactno) {
		this.contactno = contactno;
	}

	public String getLane() {
		return lane;
	}

	public void setLane(String lane) {
		this.lane = lane;
	}

	public int getBookinglimit() {
		return bookinglimit;
	}

	public void setBookinglimit(int bookinglimit) {
		this.bookinglimit = bookinglimit;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
}

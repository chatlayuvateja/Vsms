package com.example.demo.entities;




import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int customerid;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name="birthdate")
	Date birthdate;
	@Column(name="firstname")
	String firstname;
	@Column(name="lastname")
	String lastname;
	@Column(name="lane")
	String lane;
	@Column(name="contactno")
	String contactno;
	@Column(name="emailid")
	String emailid;
	
	@JoinColumn(name="loginid")
	@OneToOne
	Login login;
	
	@JoinColumn(name="areaid")
	@OneToOne
	Area area;

	public Customer() {
		super();
	}


	public Customer(Date birthdate, String firstname, String lastname, String lane, String contactno, String emailid,
			Login login, Area area) {
		super();
		this.birthdate = birthdate;
		this.firstname = firstname;
		this.lastname = lastname;
		this.lane = lane;
		this.contactno = contactno;
		this.emailid = emailid;
		this.login = login;
		this.area = area;
	}

	public int getCustomerid() {
		return customerid;
	}

	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLane() {
		return lane;
	}

	public void setLane(String lane) {
		this.lane = lane;
	}

	public String getContactno() {
		return contactno;
	}

	public void setContactno(String contactno) {
		this.contactno = contactno;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}	
}

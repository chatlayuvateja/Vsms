package com.example.demo.entities;


import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="servicerequests")
public class ServiceRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int servicerequestid;
	@Column
	boolean status;
	
	@Column
	Date bookingdate;
	
	Time pickuptime;
	
	@Column
	@NonNull
	Date servicdate;
	
	@OneToOne
	@NonNull
	@JoinColumn(name="vehicleid")
	Vehicle vehid;
	
	@JsonIgnoreProperties("login")
	@OneToOne
	@NonNull
	@JoinColumn(name="servicecenterid")
	ServiceCenter scid;
	
	@OneToOne
	@NonNull
	@JoinColumn(name="packageid")
	PackageDetails pkgid;

	public ServiceRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceRequest(Date bookingdate, Time pickuptime, Date servicdate, Vehicle vehid,
			ServiceCenter scid, PackageDetails pkgid) {
		super();
		this.bookingdate = bookingdate;
		this.pickuptime = pickuptime;
		this.servicdate = servicdate;
		this.vehid = vehid;
		this.scid = scid;
		this.pkgid = pkgid;
	}

	public int getServicerequestid() {
		return servicerequestid;
	}

	public void setServicerequestid(int servicerequestid) {
		this.servicerequestid = servicerequestid;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getBookingdate() {
		return bookingdate;
	}

	public void setBookingdate(Date bookingdate) {
		this.bookingdate = bookingdate;
	}

	public Time getPickuptime() {
		return pickuptime;
	}

	public void setPickuptime(Time pickuptime) {
		this.pickuptime = pickuptime;
	}

	public Date getServicdate() {
		return servicdate;
	}

	public void setServicdate(Date servicdate) {
		this.servicdate = servicdate;
	}

	public Vehicle getVehid() {
		return vehid;
	}

	public void setVehid(Vehicle vehid) {
		this.vehid = vehid;
	}

	public ServiceCenter getScid() {
		return scid;
	}

	public void setScid(ServiceCenter scid) {
		this.scid = scid;
	}

	public PackageDetails getPkgid() {
		return pkgid;
	}

	public void setPkgid(PackageDetails pkgid) {
		this.pkgid = pkgid;
	}
	
}

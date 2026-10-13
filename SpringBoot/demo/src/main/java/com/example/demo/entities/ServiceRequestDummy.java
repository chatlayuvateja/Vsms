package com.example.demo.entities;

import java.sql.Time;
import java.util.Date;

public class ServiceRequestDummy {

	Date bookingdate,servicdate;
	String pickuptime;
	
	int vehicleid,servicecenterid,packageid;
	public Date getBookingdate() {
		return bookingdate;
	}
	public void setBookingdate(Date bookingdate) {
		this.bookingdate = bookingdate;
	}
	public Date getServicdate() {
		return servicdate;
	}
	public void setServicdate(Date servicdate) {
		this.servicdate = servicdate;
	}
	public String getPickuptime() {
		return pickuptime;
	}
	public void setPickuptime(String pickuptime) {
		this.pickuptime = pickuptime;
	}
	public int getVehicleid() {
		return vehicleid;
	}
	public void setVehicleid(int vehicleid) {
		this.vehicleid = vehicleid;
	}
	public int getServicecenterid() {
		return servicecenterid;
	}
	public void setServicecenterid(int servicecenterid) {
		this.servicecenterid = servicecenterid;
	}
	public int getPackageid() {
		return packageid;
	}
	public void setPackageid(int packageid) {
		this.packageid = packageid;
	}
	
}

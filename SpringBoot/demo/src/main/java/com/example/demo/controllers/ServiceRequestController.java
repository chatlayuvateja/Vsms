package com.example.demo.controllers;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Customer;
import com.example.demo.entities.PackageDetails;
import com.example.demo.entities.ServiceCenter;
import com.example.demo.entities.ServiceRequest;
import com.example.demo.entities.ServiceRequestDummy;
import com.example.demo.entities.Vehicle;
import com.example.demo.services.CustomerService;
import com.example.demo.services.PackageDetailsService;
import com.example.demo.services.ServiceCenterService;
import com.example.demo.services.ServiceRequestService;
import com.example.demo.services.VehicleService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ServiceRequestController {
	@Autowired
	ServiceRequestService servservice;

	@Autowired
	VehicleService vehserv;

	@Autowired
	ServiceCenterService scserv;

	@Autowired
	PackageDetailsService pdserv;

	@Autowired
	CustomerService custserv;

	@PostMapping("/addServiceRequest")
	public ResponseEntity<ServiceRequest> addServiceRequest(@RequestBody ServiceRequestDummy servreq) {
		System.out.println("in Service Request Constroller**********************************");

		Date bookingdate = servreq.getServicdate();

		System.out.print("Booking date-" + bookingdate);

		ServiceCenter sc = scserv.getScById(servreq.getServicecenterid());

		int bookinglimit = scserv.getBookingLimitScById(servreq.getServicecenterid());
		System.out.println("Booking bookinglimit-" + bookinglimit);

		int bookingcount = servservice.getAllServReqOnADateforSC(bookingdate, sc);
		System.out.println("Booking bookingcount-" + bookingcount);

		ServiceRequest ser = null;

		if (bookingcount < bookinglimit) {
			System.out.print("in if **********************************\n");
			Vehicle veh = vehserv.getVehicleById(servreq.getVehicleid());

			PackageDetails pkd = pdserv.getPackageById(servreq.getPackageid());
			
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
			Time pickupTime = null;
			try {
				pickupTime = new Time(formatter.parse(servreq.getPickuptime()).getTime());				
			} catch (ParseException ex) {
				ex.printStackTrace();
			}

			ser = new ServiceRequest(servreq.getBookingdate(), pickupTime, servreq.getServicdate(), veh, sc, pkd);
			servservice.addServiceRequest(ser);
			return ResponseEntity.ok().body(ser);
		} else {
			System.out.print("in else **********************************");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ser);
		}

	}

	@GetMapping("/getAllServiceReqsByDateSc")
	public List<ServiceRequest> getAllServiceReqsByDateSc(@RequestParam("date") String date,
			@RequestParam("scid") int scid) {

		try {
			System.out.println(date);
			Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			ServiceCenter srvc = scserv.getScById(scid);
			if (srvc == null) {
				return Collections.emptyList();
			}
			return servservice.getAllServiceReqsByDateSc(parsedDate, srvc);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@GetMapping("/getAllServReqOnADateforSC")
	public int getAllServReqOnADateforSC(@RequestParam("date") String date, @RequestParam("scid") int scid) {
		// java.util.Date cannot bind "yyyy-MM-dd" from the query string (it
		// returned 400). Parse it the same way getAllServiceReqsByDateSc does.
		try {
			Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			ServiceCenter srvc = scserv.getScById(scid);
			if (srvc == null) {
				return 0;
			}
			return servservice.getAllServReqOnADateforSC(parsedDate, srvc);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@GetMapping("/getSrById")
	public ServiceRequest getSrById(@RequestParam("servicerequestid") int servicerequestid) {

		return servservice.getSrById(servicerequestid);
	}

	@GetMapping("/getSerReqsByCid")
	public List<ServiceRequest> getSerReqsByCid(@RequestParam("cid") int cid) {
		Customer cust = custserv.getById(cid);
		if (cust == null) {
			return Collections.emptyList();
		}
		List<Vehicle> vehs = vehserv.getVehiclesByCid(cust);
		return servservice.getSerReqsByCid(vehs);
	}
	
	@GetMapping("/changeStatus")
	public int changeStatus(@RequestParam("srid") int servicerequestid) {

		return servservice.changeStatus(servicerequestid);
	}

}

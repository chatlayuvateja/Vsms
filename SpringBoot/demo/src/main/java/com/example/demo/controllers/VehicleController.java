package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Brand;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Vehicle;
import com.example.demo.entities.VehicleRegistration;
import com.example.demo.services.BrandService;
import com.example.demo.services.CustomerService;
import com.example.demo.services.VehicleService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class VehicleController {

	@Autowired
	VehicleService vehserv;
	
	@Autowired
	BrandService brandserv;
	
	@Autowired
	CustomerService custserv;
	
	@GetMapping("/allvehicles")
	public List<Vehicle> getVehicles()
	{
		return vehserv.getVehicles();
	}
	
	@GetMapping("/getVehiclesByCid")
	public List<Vehicle> getVehiclesByCid(@RequestParam("cid") int cid)
	{
		Customer customer =custserv.getById(cid);
		if(customer==null)
		{
			return java.util.Collections.emptyList();
		}
		return vehserv.getVehiclesByCid(customer);
	}
	
	@GetMapping("/getVehiclesById")
	public Vehicle getVehicleById(@RequestParam("vid") int vid)
	{
		return vehserv.getVehicleById(vid);
	}
	
	@GetMapping("/getVehiclesByCidBid")
	public List<Vehicle> getVehiclesByCidBid(@RequestParam("cid") int cid,@RequestParam("bid") int bid)
	{
		Customer customer =custserv.getById(cid);
		Brand brand=brandserv.getById(bid);
		if(customer==null || brand==null)
		{
			return java.util.Collections.emptyList();
		}
		return vehserv.getVehiclesByCidBid(customer,brand);
		
	}
	
	
	@PostMapping("/registervehicle")
	public ResponseEntity<?> register(@RequestBody VehicleRegistration vehicle) {

		// A null return used to be serialized as HTTP 200 with an empty body,
		// so the UI reported "Vehicle Information Added." for a vehicle that
		// was never created. Fail loudly instead.
		if(vehicle.getNumber()==null || vehicle.getNumber().trim().isEmpty())
		{
			return ResponseEntity.badRequest().body(error("invalid","Vehicle number is required."));
		}
		if(vehicle.getRegyear()<1900 || vehicle.getRegyear()>2100)
		{
			return ResponseEntity.badRequest().body(error("invalid","Enter a valid registration year."));
		}

		Brand brand=vehicle.getBrandid()>0?brandserv.getById(vehicle.getBrandid()):null;
		Customer customer=vehicle.getCustomerid()>0?custserv.getById(vehicle.getCustomerid()):null;
		if(brand==null)
		{
			return ResponseEntity.badRequest().body(error("invalid","Please select a valid brand."));
		}
		if(customer==null)
		{
			return ResponseEntity.badRequest().body(error("invalid","Could not identify your customer account. Please sign in again."));
		}

		Vehicle veh=new Vehicle(vehicle.getNumber(),vehicle.getModel(),vehicle.getFueltype(),vehicle.getRegyear(),brand,customer);
		return ResponseEntity.ok(vehserv.save(veh));
	}

	/**
	 * Removes a vehicle the signed-in customer registered. The old frontend
	 * had a deleteVeh() that called this path, but no endpoint existed, so it
	 * 404'd silently - which is why it was never attached to a button.
	 */
	@DeleteMapping("/deleteVehById")
	public ResponseEntity<?> deleteVehicle(@RequestParam("vid") int vid,
			@RequestParam(value = "cid", required = false) Integer cid) {

		Vehicle veh = vehserv.getVehicleById(vid);
		if (veh == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(error("not-found", "That vehicle no longer exists."));
		}

		// Ownership: only the customer who registered it may remove it.
		if (cid != null && veh.getCustomerid() != null
				&& veh.getCustomerid().getCustomerid() != cid.intValue()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(error("forbidden", "That vehicle belongs to another customer."));
		}

		try {
			vehserv.deleteVehById(vid);
			return ResponseEntity.ok().build();
		} catch (DataIntegrityViolationException e) {
			// servicerequests.vehicleid points here - refuse rather than 500.
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error("in-use",
					"This vehicle has service history and cannot be deleted."));
		}
	}

	private java.util.Map<String,String> error(String code,String message)
	{
		java.util.Map<String,String> body=new java.util.HashMap<>();
		body.put("error",code);
		body.put("message",message);
		return body;
	}
}

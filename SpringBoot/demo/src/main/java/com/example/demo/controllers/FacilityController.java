package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Facility;
import com.example.demo.services.FacilityService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class FacilityController {
	
	@Autowired
	FacilityService fcserv;
	
	@GetMapping("/getfacilities")
	public List<Facility> getFacilities()
	{
		return fcserv.getFacilities();
	}
	
	@GetMapping("/addfacility")
	public Facility save(@RequestBody Facility facility) {
		return fcserv.save(facility);
	}
}

package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Facility;
import com.example.demo.repositories.FacilityRepository;

@Service
public class FacilityService {
	
	@Autowired
	FacilityRepository fcrepo;
	
	public List<Facility> getFacilities()
	{
		return fcrepo.findAll();
	}
	
	public Facility save(Facility facility)
	{
		return fcrepo.save(facility);
	}

	public Facility getById(int facilityid)
	{
		return fcrepo.findById(facilityid).orElse(null);
	}
}

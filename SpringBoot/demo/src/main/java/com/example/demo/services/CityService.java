package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.City;
import com.example.demo.repositories.CityRepository;

@Service
public class CityService {
	@Autowired
	CityRepository cityrepo;
	
	public City register(City city)
	{
		return cityrepo.save(city);
	}
	
	public List<City> getAll()
	{
		return cityrepo.findAll();
	}
}

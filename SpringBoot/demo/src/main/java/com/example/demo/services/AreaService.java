package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Area;
import com.example.demo.repositories.AreaRepository;

@Service
public class AreaService {
	
	@Autowired
	AreaRepository arearepo;
	
	public List<Area> getAll()
	{
		return arearepo.findAll();
	}
	
	public Area getArea(int areaid)
	{
		return arearepo.findById(areaid).get();
	}
	
	public List<Area> getByCityId(int id)
	{
		return arearepo.getByCityId(id);
	}
	
	
	
}

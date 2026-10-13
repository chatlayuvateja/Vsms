package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Brand;
import com.example.demo.entities.Customer;
import com.example.demo.repositories.BrandRepository;

@Service
public class BrandService {
	
	@Autowired
	BrandRepository brandrepo;
	
	public Brand save(Brand brand)
	{
		return brandrepo.save(brand);
	}
	
	public List<Brand> getAll()
	{
		return brandrepo.findAll();
	}
	
	public Brand getById(int brandid) {
		return brandrepo.findById(brandid).orElse(null);
	}
}

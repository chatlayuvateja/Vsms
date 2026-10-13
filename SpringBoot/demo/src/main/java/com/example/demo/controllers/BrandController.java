package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Brand;
import com.example.demo.services.BrandService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class BrandController {
	
	@Autowired
	BrandService brandserv;
	
	@GetMapping("/getBrands")
	public List<Brand> getAll()
	{
		return brandserv.getAll();
	}
	
	@PostMapping("/registerbrand")
	public Brand register(@RequestBody Brand brand) {
		
		return brandserv.save(brand);
	}
}

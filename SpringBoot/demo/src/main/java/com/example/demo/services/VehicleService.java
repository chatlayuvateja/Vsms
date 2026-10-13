package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Brand;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Vehicle;
import com.example.demo.repositories.VehicleRepository;

@Service
public class VehicleService {
	
	@Autowired
	VehicleRepository vehrepo;
	
	public Vehicle save(Vehicle vehicle)
	{
		return vehrepo.save(vehicle);
	}
	
	public List<Vehicle> getVehicles() {
		return vehrepo.findAll();
	}
	
	public List<Vehicle> getVehiclesByCid(Customer customer)
	{
		return vehrepo.getVehiclesByCid(customer);
	}
	
	public void deleteVehById(int vid) {
		 vehrepo.deleteById(vid);
	}
	
	public Vehicle getVehicleById(int vid) {
		 return vehrepo.findById(vid).orElse(null);
	}
	
	public List<Vehicle> getVehiclesByCidBid(Customer customer,Brand brand)
	{
		return vehrepo.getVehiclesByCidBid(customer,brand);
	}
	
}

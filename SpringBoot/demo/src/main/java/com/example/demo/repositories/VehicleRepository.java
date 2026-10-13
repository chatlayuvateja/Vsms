package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Brand;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
	@Query("select v from Vehicle v where customerid=:customer")
	public List<Vehicle> getVehiclesByCid(Customer customer);
	
	@Query("select v from Vehicle v where customerid=:customer and brandid=:brand ")
	public List<Vehicle> getVehiclesByCidBid(Customer customer,Brand brand);
	
//	@Query("select v from Vehicle v where vehicleid:=vid")
//	public Vehicle getVehicleById(Vehicle vid);
	
}

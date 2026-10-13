package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Customer;
import com.example.demo.repositories.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository custrepo;
	
	public Customer save(Customer customer)
	{
		return custrepo.save(customer);
	}
	
	public List<Customer> getAll()
	{
		return custrepo.findAll();
	}
	
	public Customer getById(int id)
	{
		return custrepo.findById(id).orElse(null);
	}
	
	public Customer getByLoginid(String loginid)
	{
		Customer customer;
		Optional<Customer> cust=custrepo.getByLoginid(loginid);
		try {
			customer=cust.get();
		}
		catch(Exception e) {
			customer=null;
		}
		return customer;
	}
}

package com.example.demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Customer;
import com.example.demo.entities.ServiceCenter;
import com.example.demo.entities.ServiceRequest;
import com.example.demo.entities.Vehicle;
import com.example.demo.repositories.ServiceRequestRepository;

@Service
public class ServiceRequestService {

	@Autowired
	ServiceRequestRepository servrepo;
	
	public ServiceRequest addServiceRequest(ServiceRequest servreq)
	{
		System.out.println("added request\n");
		return servrepo.save(servreq);
	}
	
	public List<ServiceRequest> getAllServiceReqsByDateSc(Date date,ServiceCenter sc)
	{
		return servrepo.getAllServiceReqsByDateSc(date,sc);
	}
	
	public ServiceRequest getSrById(int id)
	{
		return servrepo.findById(id).orElse(null);
	}
	

	public int getAllServReqOnADateforSC(Date date,ServiceCenter servc)
	{
		Integer count=servrepo.getAllServReqOnADateforSC(date,servc);
		return count==null?0:count;
	}
	
	public List<ServiceRequest> getSerReqsByCid(List<Vehicle> vehs)
	{
		return servrepo.getSerReqsByCid(vehs);
	}
	
	public int changeStatus(int servicerequestid)
	{
		return servrepo.changeStatus(servicerequestid);
	}
}

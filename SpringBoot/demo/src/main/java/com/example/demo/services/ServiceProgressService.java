package com.example.demo.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.ServiceProgress;
import com.example.demo.entities.ServiceRequest;
import com.example.demo.repositories.ServiceProgressRepository;

@Service
public class ServiceProgressService {

	@Autowired
	ServiceProgressRepository serprepo;
	
	public ServiceProgress checkIn(ServiceProgress servpro)
	{
		return serprepo.save(servpro);
	}
	
	public int addStageOne(ServiceRequest serv,Date stageone)
	{
		return serprepo.addStageOne(serv,stageone);
	}
	
	public int addStageTwo(ServiceRequest serv,Date stageone)
	{
		return serprepo.addStageTwo(serv,stageone);
	}
	
	public int addCheckOut(ServiceRequest serv,Date stageone)
	{
		return serprepo.addCheckOut(serv,stageone);
	}
	
	public int addDelivered(ServiceRequest sr)
	{
		return serprepo.addDelivered(sr);
	}
	
	public ServiceProgress getServProgIdByServreqId(ServiceRequest sr)
	{
		return serprepo.getServProgIdByServreqId(sr);
	}
	
	public ServiceProgress getScpById(int scpid)
	{
		return serprepo.findById(scpid).orElse(null);
	}
	
}

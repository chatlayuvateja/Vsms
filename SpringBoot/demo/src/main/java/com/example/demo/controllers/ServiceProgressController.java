package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.ServiceCenter;
import com.example.demo.entities.ServiceProgress;
import com.example.demo.entities.ServiceProgressDummy;
import com.example.demo.entities.ServiceRequest;
import com.example.demo.services.ServiceCenterService;
import com.example.demo.services.ServiceProgressService;
import com.example.demo.services.ServiceRequestService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ServiceProgressController {

	@Autowired
	ServiceProgressService serpserv;
	
	@Autowired
	ServiceRequestService servcreq;
	
	@PostMapping("/addCheckIn")
	public ServiceProgress checkIn(@RequestBody ServiceProgressDummy servprod)
	{
		
		ServiceRequest sr=servcreq.getSrById(servprod.getServicerequestid());
		if(sr==null)
		{
			return null;
		}
//		System.out.println("Checkin"+servprod.getCheckin());
//		System.out.println();
		ServiceProgress sp=new ServiceProgress(servprod.getCheckin(),servprod.getStageone(),servprod.getStagetwo(),servprod.getCheckout(),servprod.getDelivered(),sr);
		
		return serpserv.checkIn(sp);
	}
	
	@PostMapping("/addStageOne")
	public int stageOne(@RequestBody ServiceProgressDummy servprod)
	{
		ServiceRequest sr=servcreq.getSrById(servprod.getServicerequestid());
		return sr==null?0:serpserv.addStageOne(sr,servprod.getStageone());
	}
	
	@PostMapping("/addStageTwo")
	public int stageTwo(@RequestBody ServiceProgressDummy servprod)
	{
		ServiceRequest sr=servcreq.getSrById(servprod.getServicerequestid());
		return sr==null?0:serpserv.addStageTwo(sr,servprod.getStagetwo());
	}
	
	@PostMapping("/addCheckOut")
	public int checkOut(@RequestBody ServiceProgressDummy servprod)
	{
		ServiceRequest sr=servcreq.getSrById(servprod.getServicerequestid());
		return sr==null?0:serpserv.addCheckOut(sr,servprod.getCheckout());
	}
	
	@GetMapping("/getServProgIdByServreqId")
	public ServiceProgress getServProgIdByServreqId(@RequestParam("ServReqId") int ServReqId)
	{
		ServiceRequest sr=servcreq.getSrById(ServReqId);
		return sr==null?null:serpserv.getServProgIdByServreqId(sr);
	}
	
	@GetMapping("/getScpById")
	public ServiceProgress getScpById(@RequestParam("scpid") int scpid)
	{
		return serpserv.getScpById(scpid);
	}
	
	@GetMapping("/addDelivered")
	public int addDelivered(@RequestParam("srid") int srid)
	{
		ServiceRequest sr=servcreq.getSrById(srid);
		return sr==null?0:serpserv.addDelivered(sr);
	}
	
	
}

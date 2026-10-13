package com.example.demo.controllers;


import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Area;
import com.example.demo.entities.Brand;
import com.example.demo.entities.Login;
import com.example.demo.entities.PassBasedEnc;
import com.example.demo.entities.Role;
import com.example.demo.entities.SaltValue;
import com.example.demo.entities.ServiceCenter;
import com.example.demo.entities.ServiceCenterRegistration;
import com.example.demo.services.AreaService;
import com.example.demo.services.BrandService;
import com.example.demo.services.LoginService;
import com.example.demo.services.RoleService;
import com.example.demo.services.ServiceCenterService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ServiceCenterController {
	@Autowired
	ServiceCenterService scserv;
	
	@Autowired
	RoleService roleserv;
	@Autowired
	AreaService areaserv;
	@Autowired
	LoginService logserv;
	@Autowired
	BrandService brandserv;
	@Autowired
	LoginService loginserv;
	@Autowired
	SaltValue saltValue;
	
	@GetMapping("/allservicecenters")
	public List<ServiceCenter> getServiceCenters()
	{
		return scserv.getServiceCenters();
	}
	
	@GetMapping("/getServiceCenterbyArea")
	public List<ServiceCenter> getServiceCentersbyArea(@RequestParam("aid") int id)
	{
		return scserv.getServiceCentersbyArea(id);
	}
	
	@GetMapping("/getServiceCenterbyBrand")
	public List<ServiceCenter> getServiceCentersbyBrand(@RequestParam("bid") int id)
	{
		return scserv.getServiceCentersbyBrand(id);
	}
	
	@GetMapping("/getScDetailsByLoginid")
	public ServiceCenter getDetailsByLoginid(@RequestParam("loginid") int loginid)
	{
		Login login=loginserv.getLoginByLoginid(loginid);
		return scserv.getDetailsByLoginid(login);
	}
	
	@GetMapping("/getScByBrandArea")
	public List<ServiceCenter> getScByBrandArea(@RequestParam("bid") int brandid, @RequestParam("aid")int areaid)
	{
		return scserv.getScByBrandArea(brandid,areaid);
	}
	
	@GetMapping("/getScById")
	public ServiceCenter getScById(@RequestParam("scid") int scid)
	{
		return scserv.getScById(scid);
	}
	
	@GetMapping("/getBookingLimitScById")
	public int getBookingLimitScById(@RequestParam("scid") int scid)
	{
		return scserv.getBookingLimitScById(scid);
	}
	
	
	@PostMapping("/registerservicecenter")
	@Transactional
	public ResponseEntity<?> save(@RequestBody ServiceCenterRegistration servicecenter)
	{
		String userid=servicecenter.getUserid()==null?null:servicecenter.getUserid().trim();
		if(userid==null || userid.isEmpty())
		{
			return ResponseEntity.badRequest().body(error("invalid","User ID is required."));
		}

		// login.userid is UNIQUE. Without this check the INSERT throws
		// SQLIntegrityConstraintViolationException and the client is only
		// ever shown a bare 500 -> "Registration Failed" with no reason.
		if(logserv.getUserByUserid(userid)!=null)
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body(
					error("duplicate-userid","That user ID is already taken. Please choose a different one."));
		}

		// A missing brand/area used to be saved as a NULL foreign key,
		// which failed after the login row had already been inserted.
		Brand brand=servicecenter.getBrandid()>0?brandserv.getById(servicecenter.getBrandid()):null;
		Area area=servicecenter.getAreaid()>0?areaserv.getArea(servicecenter.getAreaid()):null;
		if(brand==null)
		{
			return ResponseEntity.badRequest().body(error("invalid","Please select a valid brand."));
		}
		if(area==null)
		{
			return ResponseEntity.badRequest().body(error("invalid","Please select a valid area."));
		}
		if(servicecenter.getBookinglimit()<=0)
		{
			return ResponseEntity.badRequest().body(error("invalid","Booking limit must be a number greater than zero."));
		}

		Role role=roleserv.getRole(2);
		int questionid=servicecenter.getQuestionid();

		String encrypted=PassBasedEnc.generateSecurePassword(servicecenter.getPassword(),saltValue.getSalt());

		// @Transactional: if either insert fails neither one is committed,
		// so a half-created account can never be left behind.
		Login login =new Login(userid,encrypted,servicecenter.getAnswer(),false,role,questionid);
		Login saved=logserv.save(login);

		ServiceCenter sc=new ServiceCenter(servicecenter.getScname(),servicecenter.getEmailid(),servicecenter.getContactno(),servicecenter.getLane(),servicecenter.getBookinglimit(),saved,brand,area);
		return ResponseEntity.ok(scserv.save(sc));
	}

	private Map<String,String> error(String code,String message)
	{
		Map<String,String> body=new java.util.HashMap<>();
		body.put("error",code);
		body.put("message",message);
		return body;
	}

}

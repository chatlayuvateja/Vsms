package com.example.demo.controllers;

import java.io.IOException;

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
import com.example.demo.entities.Customer;
import com.example.demo.entities.CustomerRegistration;
import com.example.demo.entities.Login;
import com.example.demo.entities.PassBasedEnc;
import com.example.demo.entities.Role;
import com.example.demo.entities.SaltValue;
import com.example.demo.services.AreaService;
import com.example.demo.services.CustomerService;
import com.example.demo.services.LoginService;
import com.example.demo.services.RoleService;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CustomerController {
	@Autowired
	CustomerService custserv;
	@Autowired
	RoleService roleserv;
	@Autowired
	AreaService areaserv;
	@Autowired
	LoginService logserv;
	
	@Autowired
	SaltValue saltValue;
	
	@GetMapping("/getCustomers")
	public List<Customer> getAll()
	{
		return custserv.getAll();
	}
	
	@GetMapping("/getCustomerdetails")
	public Customer getByLoginid(@RequestParam("loginid") String loginid)
	{
		return custserv.getByLoginid(loginid);
	}
	
	@PostMapping("/registercustomer")
	@Transactional
	public ResponseEntity<?> register(@RequestBody CustomerRegistration custreg) {
		String userid=custreg.getUserid()==null?null:custreg.getUserid().trim();
		if(userid==null || userid.isEmpty())
		{
			return ResponseEntity.badRequest().body(error("invalid","User ID is required."));
		}

		// login.userid is UNIQUE. A duplicate used to throw
		// SQLIntegrityConstraintViolationException, so the client only ever
		// saw a bare 500 -> "Registration Failed" with no reason.
		if(logserv.getUserByUserid(userid)!=null)
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body(
					error("duplicate-userid","That user ID is already taken. Please choose a different one."));
		}

		// Guard the foreign key before writing anything, and keep both
		// inserts in one transaction so a failure can never leave an
		// orphaned login row behind.
		Area area=custreg.getAreaid()>0?areaserv.getArea(custreg.getAreaid()):null;
		if(area==null)
		{
			return ResponseEntity.badRequest().body(error("invalid","Please select a valid area."));
		}
		if(custreg.getFname()==null || custreg.getFname().trim().isEmpty())
		{
			return ResponseEntity.badRequest().body(error("invalid","First name is required."));
		}

		Role role=roleserv.getRole(1);
		int questionid=custreg.getQuestionid();

		String encrypted=PassBasedEnc.generateSecurePassword(custreg.getPassword(),saltValue.getSalt());

		Login login =new Login(userid,encrypted,custreg.getAnswer(),true,role,questionid);
		Login saved=logserv.save(login);

		// Constructor order is (…, lane, contactno, emailid, …). Passing
		// (contactno, emailid, lane) here rotated all three columns: the
		// address landed in contactno, the phone number in emailid, etc.
		Customer customer=new Customer(custreg.getBirthdate(),custreg.getFname(),custreg.getLname(),custreg.getLane(),custreg.getContactno(),custreg.getEmailid(),saved,area);

		return ResponseEntity.ok(custserv.save(customer));
	}

	private Map<String,String> error(String code,String message)
	{
		Map<String,String> body=new java.util.HashMap<>();
		body.put("error",code);
		body.put("message",message);
		return body;
	}
}















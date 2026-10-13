package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.demo.entities.Rating;
import com.example.demo.entities.RatingDummy;
import com.example.demo.entities.Role;
import com.example.demo.entities.ServiceCenter;
import com.example.demo.services.CustomerService;
import com.example.demo.services.RatingService;
import com.example.demo.services.RoleService;
import com.example.demo.services.ServiceCenterService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RatingController {

	@Autowired
	RatingService rserv;
	
	@Autowired
	ServiceCenterService scserv;
	
	@Autowired
	CustomerService custserv;
	
	@PostMapping("/setRating")
	public ResponseEntity<?> setRating(@RequestBody RatingDummy rt) 
	{
		if (rt.getRating() < 1 || rt.getRating() > 5) {
			return ResponseEntity.badRequest()
					.body(error("invalid", "Choose a rating between 1 and 5 stars."));
		}
		if (rt.getServicecenterid() <= 0) {
			return ResponseEntity.badRequest()
					.body(error("invalid", "Select the service centre you want to rate."));
		}

		// Was returning null, which serialises to an empty 200 body - the UI
		// then told the customer their feedback had been sent.
		Customer c = custserv.getById(rt.getCustomerid());
		if (c == null) {
			return ResponseEntity.badRequest()
					.body(error("invalid", "Could not identify your account."));
		}
		ServiceCenter s = scserv.getScById(rt.getServicecenterid());
		if (s == null) {
			return ResponseEntity.badRequest()
					.body(error("invalid", "That service centre no longer exists."));
		}

		Rating rts = new Rating(rt.getRating(), rt.getComment(), c, s);
		return ResponseEntity.ok(rserv.setRating(rts));
	}
	
	@GetMapping("/getRating")
	public ResponseEntity<?> getRating(@RequestParam("scid") int scid) {
		ServiceCenter sc = scserv.getScById(scid);
		if (sc == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(error("not-found", "No such service centre."));
		}
		return ResponseEntity.ok(rserv.getRating(sc));
	}

	private java.util.Map<String, String> error(String code, String message)
	{
		java.util.Map<String, String> body = new java.util.HashMap<>();
		body.put("error", code);
		body.put("message", message);
		return body;
	}
	
	
}

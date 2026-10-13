package com.example.demo.controllers;

import java.util.List;


import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Login;
import com.example.demo.entities.PassBasedEnc;
import com.example.demo.entities.Question;
import com.example.demo.entities.SaltValue;
import com.example.demo.services.LoginService;
import com.example.demo.services.QuestionService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LoginController {
	
	@Autowired
	LoginService logserv;
	
	@Autowired
	QuestionService qserv;
	
	@Autowired
	SaltValue saltValue;
	
	@GetMapping("/all")
	public List<Login> getAllUsers()
	{
		return logserv.getAllUsers();
	}
	/*
	@PostMapping("/logincheck")
	public ResponseEntity<Login> getByDetails(@RequestBody Login userlogin)
	{
		List<Login> users = logserv.getAllUsers();
		
		String uid = userlogin.getUserid();
		String pwd = userlogin.getPassword();
			
		//System.out.println(userlogin.getUserid());
		ListIterator<Login> iterator = users.listIterator();  
	
		while (iterator.hasNext())  
		{  
			Login u = iterator.next(); 
			if(uid.equals(u.getUserid()) && pwd.equals(u.getPassword())) 
			{
				System.out.println("User Login Details matched");
				return ResponseEntity.ok().body(u);
			}
		}  
		System.out.println("User Login Details NOT matched");
		 return ResponseEntity.notFound().build();
		
	}*/
	
	@PostMapping("/logincheck")
	public ResponseEntity<?> logincheck(@RequestBody Login login)
	{
		String encrypted=PassBasedEnc.generateSecurePassword(login.getPassword(),saltValue.getSalt());
		Login valid=logserv.getLogin(login.getUserid(), encrypted);
		if(valid!=null)
		{
			return ResponseEntity.ok(valid);
		}

		// getLogin also requires status=1, so "account not approved" and
		// "wrong password" both come back as null. Tell them apart so the UI
		// can show an actionable message instead of "invalid credentials".
		Login existing=logserv.getUserByUserid(login.getUserid());
		if(existing!=null && !existing.isStatus())
		{
			Map<String,Object> body=new java.util.HashMap<>();
			body.put("error","pending");
			body.put("message","This account is awaiting approval by the administrator.");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
		}

		// No such user, or wrong password.
		return ResponseEntity.ok().body(null);
	}
	
	@GetMapping("/getLoginByLoginid")
	public Login getLoginByLoginid(@RequestParam("loginid") int loginid)
	{
		return logserv.getLoginByLoginid(loginid);
	}
	
	@PostMapping("/checkAnswer")
	public Login checkLoginAnswer(@RequestParam("userid") String userid,@RequestParam("answer") String answer)
	{		
		return logserv.checkLoginAnswer(answer,userid);
	}
	
	@PostMapping("/changePwd")
	public int changePassword(@RequestParam("newPwd") String newpassword,@RequestParam("userid") String userid )
	{
		String encrypted=PassBasedEnc.generateSecurePassword(newpassword,saltValue.getSalt());
		return  logserv.changePassword(encrypted,userid);
	}
	
	@PatchMapping("/updateReq/{loginid}")
	public Login updateReq(@PathVariable("loginid") int loginid,@RequestBody Map<String,Object> field)
	{
		return logserv.updateByField(loginid,field);
		
	}
}

package com.example.demo.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Login;
import com.example.demo.entities.Question;
import com.example.demo.repositories.LoginRepository;


@Service
public class LoginService {

	@Autowired
	LoginRepository logrepo;
	
	public List<Login> getAllUsers()
	{
		return logrepo.findAll();
	}
	
	public Login getLogin(String uid,String pwd) {
		Login login;
		Optional<Login> ol=logrepo.getLogin(uid, pwd);
		try {
			login=ol.get();
		}catch(Exception e)
		{
			login=null;
			//e.printStackTrace();
		}
		return login;
	}
	
	public Login save(Login login) {
		return logrepo.save(login);
	}
	
	public Login getLoginByLoginid(int loginid)
	{
		return logrepo.getLoginByLoginid(loginid);
	}
	
	public Login checkLoginAnswer(String answer,String userid)
	{
		return logrepo.checkLoginAnswer(answer,userid);
	}
	
	public Login getUserByUserid(String userid)
	{
		return logrepo.getUserByUserid(userid);
	}
	
	public int changePassword(String newpassword,String userid)
	{
		return logrepo.changePassword(newpassword,userid);
	}
	
	public Login updateByField(int id,Map<String,Object> fields) 
	{	
	    Optional<Login> l = logrepo.findById(id);
	    if(l.isPresent())
	    {
	    	fields.forEach((key,value)->{
	    	java.lang.reflect.Field field = ReflectionUtils.findRequiredField(Login.class,key);
	    	field.setAccessible(true);
	    	ReflectionUtils.setField(field, l.get(), value);
	    });
		return logrepo.save(l.get());
	    }
	    return null;
	  }
	
}

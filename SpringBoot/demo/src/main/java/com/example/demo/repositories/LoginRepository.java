package com.example.demo.repositories;

import java.util.Optional;

import javax.transaction.Transactional;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Login;


@Transactional
@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {
	
//	@Query("select l from Login l where userid=:uid and password=:pwd")
//	public Optional<Login> getLogin(String uid,String pwd);
	
	@Query("select l from Login l where loginid=:loginid")
	public Login getLoginByLoginid(int loginid);
	
	@Query("select l from Login l where userid =:userid and answer=:answer")
	public Login checkLoginAnswer(String answer,String userid);
	
	
	@Query("select l from Login l where userid=:userid")
	public Login getUserByUserid(String userid);
	
	@Query("select l from Login l where userid=:uid and password=:pwd and status=1")
	public Optional<Login> getLogin(String uid,String pwd);
	
	@Modifying
	@Query("UPDATE Login SET password=:newpassword where userid=:userid")
	public int changePassword(String newpassword,String userid);
}

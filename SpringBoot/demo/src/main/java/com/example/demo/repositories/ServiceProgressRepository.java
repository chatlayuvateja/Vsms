package com.example.demo.repositories;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import com.example.demo.entities.ServiceProgress;
import com.example.demo.entities.ServiceRequest;

@Transactional
@Repository
public interface ServiceProgressRepository extends JpaRepository<ServiceProgress, Integer>  {

	 @Modifying
	 @Query("UPDATE ServiceProgress SET stageone = :stageone WHERE servicerequestid = :serv")
	 public int addStageOne (ServiceRequest serv,Date stageone);
	 
	 @Modifying
	 @Query("UPDATE ServiceProgress SET stagetwo = :stagetwo WHERE servicerequestid = :serv")
	 public int addStageTwo (ServiceRequest serv,Date stagetwo);
	 
	 @Modifying
	 @Query("UPDATE ServiceProgress SET checkout = :checkout WHERE servicerequestid = :serv")
	 public int addCheckOut (ServiceRequest serv,Date checkout);
	 
	 @Query("select sp from ServiceProgress sp where servicerequestid=:sr")
	 public ServiceProgress getServProgIdByServreqId(ServiceRequest sr);
	 
	 @Modifying
	 @Query("Update ServiceProgress SET delivered=true WHERE servicerequestid=:sr")
	 public int addDelivered(ServiceRequest sr);
}

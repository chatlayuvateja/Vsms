package com.example.demo.repositories;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.ServiceCenter;
import com.example.demo.entities.ServiceRequest;
import com.example.demo.entities.Vehicle;


@Repository
@Transactional
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Integer> {

	@Query("select sr from ServiceRequest sr where servicdate=:date and servicecenterid=:sc")
	public List<ServiceRequest> getAllServiceReqsByDateSc(Date date,ServiceCenter sc);
	
	@Query("select count(*) from ServiceRequest sr where servicdate=:date and servicecenterid=:servc")
	public Integer getAllServReqOnADateforSC(Date date,ServiceCenter servc);
	
	@Query(nativeQuery =true,value ="select * from servicerequests where vehicleid in (:vehs)")
	public List<ServiceRequest> getSerReqsByCid(@Param("vehs") List<Vehicle> vehs);
	
	@Modifying
	@Query("Update ServiceRequest set status=true where servicerequestid=:servicerequestid")
	public int changeStatus(int servicerequestid);
}

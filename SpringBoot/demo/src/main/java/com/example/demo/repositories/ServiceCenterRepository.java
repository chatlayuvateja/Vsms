package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Login;
import com.example.demo.entities.ServiceCenter;

@Repository
public interface ServiceCenterRepository extends JpaRepository<ServiceCenter, Integer> {
	@Query("select sc from ServiceCenter sc where areaid=:aid")
	public List<ServiceCenter> getServiceCentersbyArea(int aid);
	
	@Query("select sc from ServiceCenter sc where brandid=:bid")
	public List<ServiceCenter> getServiceCentersbyBrand(int bid);
	
	@Query("select sc from ServiceCenter sc where brandid=:brandid and areaid=:areaid")
	public List<ServiceCenter> getScByBrandArea(int brandid, int areaid);
	
	@Query("select sc.bookinglimit from ServiceCenter sc where servicecenterid=:scid")
	public Integer getBookingLimitScById(int scid);
	
	
	@Query("select sc from ServiceCenter sc where loginid=:loginid")
	public ServiceCenter getDetailsByLoginid(Login loginid);
}

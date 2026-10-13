package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.PackageDetails;

@Transactional
@Repository
public interface PackageDetailsRepository extends JpaRepository<PackageDetails, Integer> {
	@Query("select pd from PackageDetails pd where servicecenterid=:scid")
	public List<PackageDetails> getPackageByScId(int scid);
}

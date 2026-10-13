package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Rating;
import com.example.demo.entities.ServiceCenter;
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
	@Query("select floor(avg(rating)) from Rating group by servicecenterid having servicecenterid=:sc")
	public Integer getRating(ServiceCenter sc);
}

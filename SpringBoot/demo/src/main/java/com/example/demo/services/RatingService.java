package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Rating;
import com.example.demo.entities.ServiceCenter;
import com.example.demo.repositories.RatingRepository;

@Service
public class RatingService {

	@Autowired
	RatingRepository rtrepo;
	
	public Rating setRating(Rating rt)
	{
		return rtrepo.save(rt);
	}
	
	public int getRating(ServiceCenter sc)
	{
		if(sc==null) {
			return 0;
		}
		Integer rating=rtrepo.getRating(sc);
		return rating==null?0:rating;
	}
}

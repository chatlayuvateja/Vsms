package com.example.demo.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Question;
import com.example.demo.repositories.QuestionRepository;

@Service
public class QuestionService {
	@Autowired
	QuestionRepository qrepo;

	public List<Question> getAll() {
		
		return qrepo.findAll();
	}
	
	public Question getByID(int id)
	{
		return qrepo.findById(id).get();
	}
	
	
}

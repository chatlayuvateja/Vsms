package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entities.Transaction;
import com.example.demo.repositories.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	TransactionRepository trrepo;
	
	public Transaction addTransaction(Transaction transaction)
	{
		return trrepo.save(transaction);
	}
	
	public Transaction getTrById(int trid)
	{
		return trrepo.findById(trid).get();
	}
}

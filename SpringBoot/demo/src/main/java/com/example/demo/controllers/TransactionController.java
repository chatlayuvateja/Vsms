package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.ServiceProgress;
import com.example.demo.entities.Transaction;
import com.example.demo.entities.TransactionDummy;
import com.example.demo.services.ServiceProgressService;
import com.example.demo.services.TransactionService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TransactionController {

	
	@Autowired
	TransactionService trserv;
	
	@Autowired
	ServiceProgressService scpserv;
	
	@PostMapping("/addTransaction")
	public Transaction addTransaction(@RequestBody TransactionDummy transaction)
	{
		ServiceProgress scp=scpserv.getScpById(transaction.getServiceprogressid());
		
		Transaction tr=new Transaction(transaction.getAmount(),transaction.getDate(),transaction.getPaymentmode(),scp);
		return trserv.addTransaction(tr);
	}
	
	
}

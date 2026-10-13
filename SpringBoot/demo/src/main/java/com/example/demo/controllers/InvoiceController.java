package com.example.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Invoice;
import com.example.demo.entities.InvoiceDummy;
import com.example.demo.entities.ServiceRequest;
import com.example.demo.entities.Transaction;
import com.example.demo.services.InvoiceService;
import com.example.demo.services.ServiceRequestService;
import com.example.demo.services.TransactionService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class InvoiceController {

	@Autowired
	private InvoiceService inserv;
	
	@Autowired
	ServiceRequestService srserv;
	
	@Autowired
	TransactionService trserv;
	
	@GetMapping("/getInvById")
	public Invoice getInvById(@RequestParam("invcId") int invcId)
	{
		return inserv.getInvById(invcId);
	}
	
	@PostMapping("/createInvoice")
	public Invoice createInvoice(@RequestBody InvoiceDummy invd)
	{
		ServiceRequest sr=srserv.getSrById(invd.getServicerequestid());
		Transaction tr=trserv.getTrById(invd.getTransactionid());
		Invoice invoice =new Invoice(true,sr,tr);
		return inserv.createInvoice(invoice);
	}
	
	@GetMapping("/getInvoiceBySvReqId")
	public Invoice getInvoiceBySvReqId(@RequestParam("sreqid") int srqid)
	{
		ServiceRequest sr=srserv.getSrById(srqid);
		if(sr==null)
		{
			return null;
		}
		return inserv.getInvoiceBySvReqId(sr);
	}
	
}

package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Invoice;
import com.example.demo.entities.ServiceRequest;
import com.example.demo.repositories.InvoiceRepository;

@Service
public class InvoiceService {

	@Autowired
	InvoiceRepository invrepo;
	
	public Invoice getInvById(int invcId)
	{
		return invrepo.findById(invcId).get();
	}
	
	public Invoice createInvoice(Invoice invoice)
	{
		return invrepo.save(invoice);
	}
	
	public Invoice getInvoiceBySvReqId(ServiceRequest srqid)
	{
		return invrepo.getInvoiceBySvReqId(srqid);
	}
}
package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Invoice;
import com.example.demo.entities.ServiceRequest;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	@Query("select i from Invoice i where servicerequestid=:srqid")
	public Invoice getInvoiceBySvReqId(ServiceRequest srqid);
}

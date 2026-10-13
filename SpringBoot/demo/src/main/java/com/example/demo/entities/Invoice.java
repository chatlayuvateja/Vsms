package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="invoices")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int invoiceid;
	
	private boolean status;
	
	@JoinColumn(name="servicerequestid")
	@OneToOne
	ServiceRequest servicerequestid;
	
	@JoinColumn(name="transactionid")
	@OneToOne
	Transaction transactionid;

	public Invoice() {
		super();
	}

	public Invoice(boolean status, ServiceRequest servicerequestid, Transaction transactionid) {
		super();
		this.status = status;
		this.servicerequestid = servicerequestid;
		this.transactionid = transactionid;
	}

	public int getInvoiceid() {
		return invoiceid;
	}

	public void setInvoiceid(int invoiceid) {
		this.invoiceid = invoiceid;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public ServiceRequest getServicerequestid() {
		return servicerequestid;
	}

	public void setServicerequestid(ServiceRequest servicerequestid) {
		this.servicerequestid = servicerequestid;
	}

	public Transaction getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(Transaction transactionid) {
		this.transactionid = transactionid;
	}
	
}

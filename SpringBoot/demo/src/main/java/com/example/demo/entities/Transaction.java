package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int transactionid;
	
	double amount;
	Date date;
	String paymentmode;
	
	
	@JoinColumn(name="serviceprogressid")
	@OneToOne
	ServiceProgress serviceprogres;
	
	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Transaction(double amount, Date date, String paymentmode, ServiceProgress serviceprogres) {
		super();
		this.amount = amount;
		this.date = date;
		this.paymentmode = paymentmode;
		this.serviceprogres = serviceprogres;
	}

	public ServiceProgress getServiceprogres() {
		return serviceprogres;
	}

	public void setServiceprogres(ServiceProgress serviceprogres) {
		this.serviceprogres = serviceprogres;
	}



	public int getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}
	
}

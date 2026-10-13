package com.example.demo.entities;

import java.util.Date;

public class TransactionDummy {
	private double amount;
	private Date date;
	private String paymentmode;
	private int serviceprogressid;
	
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
	public int getServiceprogressid() {
		return serviceprogressid;
	}
	public void setServiceprogressid(int serviceprogressid) {
		this.serviceprogressid = serviceprogressid;
	}
	
	
}

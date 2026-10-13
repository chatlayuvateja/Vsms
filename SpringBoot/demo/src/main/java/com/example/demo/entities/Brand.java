package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="brands")
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int brandid;
	@Column
	String bname;
	
	public Brand() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Brand(int brandid, String bname) {
		super();
		this.brandid = brandid;
		this.bname = bname;
	}
	
	public int getBrandid() {
		return brandid;
	}
	
	public void setBrandid(int brandid) {
		this.brandid = brandid;
	}
	
	public String getBname() {
		return bname;
	}
	
	public void setBname(String bname) {
		this.bname = bname;
	}
}

package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="serviceprogress")
public class ServiceProgress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int serviceprogressid;
	
	Date checkin;
	
	Date stageone;
	
	Date stagetwo;
	
	Date checkout;
	
	boolean delivered;
	
	@JoinColumn(name="servicerequestid")
	@OneToOne
	ServiceRequest servicerequestid;

	public ServiceProgress() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceProgress(Date checkin, Date stageone, Date stagetwo, Date checkout, boolean delivered,
			ServiceRequest servicerequestid) {
		super();
		this.checkin = checkin;
		this.stageone = stageone;
		this.stagetwo = stagetwo;
		this.checkout = checkout;
		this.delivered = delivered;
		this.servicerequestid = servicerequestid;
	}

	public int getServiceprogressid() {
		return serviceprogressid;
	}

	public void setServiceprogressid(int serviceprogressid) {
		this.serviceprogressid = serviceprogressid;
	}

	public Date getCheckin() {
		return checkin;
	}

	public void setCheckin(Date checkin) {
		this.checkin = checkin;
	}

	public Date getStageone() {
		return stageone;
	}

	public void setStageone(Date stageone) {
		this.stageone = stageone;
	}

	public Date getStagetwo() {
		return stagetwo;
	}

	public void setStagetwo(Date stagetwo) {
		this.stagetwo = stagetwo;
	}

	public Date getCheckout() {
		return checkout;
	}

	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}

	public boolean getDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	public ServiceRequest getServicerequestid() {
		return servicerequestid;
	}

	public void setServicerequestid(ServiceRequest servicerequestid) {
		this.servicerequestid = servicerequestid;
	}
}

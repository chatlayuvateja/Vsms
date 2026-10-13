package com.example.demo.entities;

import java.util.Date;

public class ServiceProgressDummy {

	Date checkin, stageone, stagetwo, checkout;
	boolean delivered; 
	int servicerequestid;
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
	public int getServicerequestid() {
		return servicerequestid;
	}
	public void setServicerequestid(int servicerequestid) {
		this.servicerequestid = servicerequestid;
	}
	
}

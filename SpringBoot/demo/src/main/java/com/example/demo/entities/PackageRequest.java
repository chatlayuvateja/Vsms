package com.example.demo.entities;

import java.util.List;

/**
 * Write-side contract for creating and updating a service package.
 *
 * The entity itself cannot be used here: its ServiceCenter is a live managed
 * object and its facilities arrive as a set of detached ids, so binding the
 * request straight onto PackageDetails either failed to resolve the owning
 * centre or silently wrote join rows for facilities that do not exist.
 * The controller resolves both from these ids instead.
 *
 * packagedetailsid == 0 means "create"; anything else means "update that row".
 */
public class PackageRequest {

	private int packagedetailsid;
	private String packagename;
	private double cost;
	private int servicecenterid;
	private List<Integer> facilityids;

	public int getPackagedetailsid() {
		return packagedetailsid;
	}

	public void setPackagedetailsid(int packagedetailsid) {
		this.packagedetailsid = packagedetailsid;
	}

	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getServicecenterid() {
		return servicecenterid;
	}

	public void setServicecenterid(int servicecenterid) {
		this.servicecenterid = servicecenterid;
	}

	public List<Integer> getFacilityids() {
		return facilityids;
	}

	public void setFacilityids(List<Integer> facilityids) {
		this.facilityids = facilityids;
	}
}

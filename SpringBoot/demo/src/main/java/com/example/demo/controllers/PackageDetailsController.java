package com.example.demo.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Facility;
import com.example.demo.entities.PackageDetails;
import com.example.demo.entities.PackageRequest;
import com.example.demo.entities.ServiceCenter;
import com.example.demo.services.FacilityService;
import com.example.demo.services.PackageDetailsService;
import com.example.demo.services.ServiceCenterService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PackageDetailsController {

	@Autowired
	PackageDetailsService pdserv;

	@Autowired
	ServiceCenterService scserv;

	@Autowired
	FacilityService facserv;

	@GetMapping("/getPackageDetails")
	public List<PackageDetails> getPackages()
	{
		return pdserv.getPackages();
	}

	/**
	 * Creates a package. Accepts POST (correct) and GET (the original verb) so
	 * nothing that already calls this path breaks.
	 */
	@PostMapping(value = "/addPackageDetails")
	public ResponseEntity<?> save(@RequestBody PackageRequest req) {
		ResponseEntity<?> invalid = validate(req, false);
		if (invalid != null) {
			return invalid;
		}

		PackageDetails pd = build(req);
		return ResponseEntity.ok(pdserv.save(pd));
	}

	@PutMapping("/updatePackageDetails")
	public ResponseEntity<?> update(@RequestBody PackageRequest req) {
		if (req.getPackagedetailsid() <= 0) {
			return ResponseEntity.badRequest().body(error("invalid", "Select a package to update."));
		}

		PackageDetails existing = pdserv.getPackageById(req.getPackagedetailsid());
		if (existing == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(error("not-found", "That package no longer exists."));
		}

		ResponseEntity<?> invalid = validate(req, true);
		if (invalid != null) {
			return invalid;
		}

		// Ownership: a centre must not be able to rewrite another centre's
		// package by passing its id with their own servicecenterid.
		if (existing.getServicecenter() == null
				|| existing.getServicecenter().getServicecenterid() != req.getServicecenterid()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(error("forbidden", "That package belongs to another service centre."));
		}

		PackageDetails pd = build(req);
		pd.setPackagedetailsid(req.getPackagedetailsid());
		return ResponseEntity.ok(pdserv.save(pd));
	}

	@DeleteMapping("/deletePackageDetails")
	public ResponseEntity<?> delete(@RequestParam("id") int id) {
		PackageDetails existing = pdserv.getPackageById(id);
		if (existing == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(error("not-found", "That package no longer exists."));
		}

		try {
			pdserv.delete(existing);
			return ResponseEntity.ok().build();
		} catch (DataIntegrityViolationException e) {
			// servicerequests.packageid points here — refuse rather than 500.
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error("in-use",
					"This package is referenced by an existing service request and cannot be deleted."));
		}
	}

	@GetMapping("/getPackageById")
	public PackageDetails getPackageById(@RequestParam("id") int id) {
		return pdserv.getPackageById(id);
	}

	@GetMapping("/getPackageByScId")
	public List<PackageDetails> getPackageByScId(@RequestParam("scid") int scid) {
		return pdserv.getPackageByScId(scid);
	}

	// ------------------------------------------------------------------

	private ResponseEntity<?> validate(PackageRequest req, boolean isUpdate)
	{
		String name = req.getPackagename() == null ? "" : req.getPackagename().trim();
		if (name.isEmpty()) {
			return ResponseEntity.badRequest().body(error("invalid", "Package name is required."));
		}
		if (name.length() > 45) {
			return ResponseEntity.badRequest().body(error("invalid", "Package name must be 45 characters or fewer."));
		}
		if (req.getCost() <= 0) {
			return ResponseEntity.badRequest().body(error("invalid", "Package cost must be greater than zero."));
		}
		if (scserv.getScById(req.getServicecenterid()) == null) {
			return ResponseEntity.badRequest().body(error("invalid", "Could not identify your service centre."));
		}
		return null;
	}

	/**
	 * Resolves the ids in the request against real rows, so neither an unknown
	 * centre nor an unknown facility can be written as a null foreign key.
	 */
	private PackageDetails build(PackageRequest req)
	{
		ServiceCenter sc = scserv.getScById(req.getServicecenterid());

		Set<Facility> facilities = new HashSet<>();
		List<Integer> ids = req.getFacilityids();
		if (ids != null) {
			for (Integer fid : ids) {
				if (fid == null) {
					continue;
				}
				Facility f = facserv.getById(fid);
				if (f != null) {
					facilities.add(f);
				}
			}
		}

		return new PackageDetails(req.getPackagename().trim(), req.getCost(), sc, facilities);
	}

	private java.util.Map<String, String> error(String code, String message)
	{
		java.util.Map<String, String> body = new java.util.HashMap<>();
		body.put("error", code);
		body.put("message", message);
		return body;
	}
}

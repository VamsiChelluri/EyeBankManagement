package com.accolite.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accolite.entity.Donor;
import com.accolite.repository.DonorRepo;
import com.accolite.exception.UserNotFoundException;

@RestController
public class PhysicianController {
	private static Logger logger=LoggerFactory.getLogger(PhysicianController.class);
	@Autowired
	DonorRepo donorRepo;
	
	@GetMapping("/registered")
	public ResponseEntity<List<Donor>> getDonorBasedOnRegistration(){
		logger.info("fetching donor details who are registered");
		List<Donor> donor=donorRepo.findAll();
		return new ResponseEntity<List<Donor>>(donor,HttpStatus.FOUND);
	}
	
	@PutMapping("/approval/{id}/{approval}")
	public ResponseEntity<Donor> updateApproval(@PathVariable Long id,@PathVariable String approval){
		logger.warn("updating donor approval status");
		
		int donor=donorRepo.updateApprovalBasedOnName(id,approval);
		if(donor==0) {
			throw new UserNotFoundException("Id- "+id);
		}
		return new ResponseEntity<Donor>(HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/approval/{id}")
	public ResponseEntity<Donor> updateApprovalBasedOnMedicalHistory(@PathVariable("id") Long id){
		logger.info("updating donor approval status based on medical history for donor "+id);
		Donor donor = donorRepo.findDonorById(id);
		if(donor==null) {
			logger.info("no donor found for the id "+id);
			throw new UserNotFoundException("No donor found for Id: "+id);
		}else {
			logger.info("Donor: "+donor.getName());	
			if(donor.getMedicalHistory().contains("eye")){
				donor.setApproval("rejected");
			}else {
				donor.setApproval("approved");
			}
			donor = donorRepo.save(donor);
			logger.info("updated donor approval status based on medical history for donor "+id);
		}
		return ResponseEntity.ok().body(donor);
	}
	
	@PutMapping("/additionalinfo/{id}/{extraInfo}")
	public ResponseEntity<Donor> getExtraInfo(@PathVariable Long id,@PathVariable String extraInfo){
		logger.info("updating extra info in donor");
		int donor=donorRepo.updateExtraInfo(id,extraInfo);
		if(donor==0) {
			throw new UserNotFoundException("Id- "+id);
		}
		return new ResponseEntity<Donor>(HttpStatus.ACCEPTED);
	}
	

}

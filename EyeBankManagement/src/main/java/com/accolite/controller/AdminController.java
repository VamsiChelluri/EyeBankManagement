package com.accolite.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accolite.entity.Donor;

import com.accolite.entity.Recipient;
import com.accolite.repository.DonorRepo;
import com.accolite.repository.RecipientRepo;
import com.accolite.exception.UserNotFoundException;

@Transactional
@RestController
public class AdminController {
	private static final Logger logger=LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private DonorRepo donorRepo;
	
	@Autowired
	private RecipientRepo recipientRepo;
	
	@GetMapping("/admin-getDonors")
	public ResponseEntity<List<Donor>> findDonors(){
		logger.info("retrieving donor details from admin");
		List<Donor> donor=donorRepo.findAll();
		return new ResponseEntity<List<Donor>>(donor,HttpStatus.FOUND);
	}
	
	@GetMapping("/admin-getRecipients")
	public ResponseEntity<List<Recipient>> findRecipients(){
		logger.info("retrieving donor details from admin");
		List<Recipient> recipient=recipientRepo.findAll();
		return new ResponseEntity<List<Recipient>>(recipient, HttpStatus.FOUND);
	}
	
	@PutMapping("/admin-updateApproval/{id}/{approval}")
	public ResponseEntity<Recipient> updateRecipient(@PathVariable Long id,@PathVariable String approval){
		
		logger.warn("updating approval status of the recipient");
		
		int rr = recipientRepo.updateRecipientBasedOnName(id,approval);
		if(rr==0) {
			throw new UserNotFoundException("Id- "+id);
		}
		//recipientRepo.updateRecipientBasedOnName(id,approval);
		return new ResponseEntity<Recipient>(HttpStatus.ACCEPTED);
	}

	
	  @GetMapping("/admin/Donor-status") 
	  ResponseEntity<List<Object>> getDonorStatus(){
	  logger.info("fetching donor name and approval status");
	  List<Object> donor=donorRepo.findByApproval();
	  return new ResponseEntity<List<Object>>( donor, HttpStatus.FOUND); 
	  }
	 
	
	@DeleteMapping("/admin/delete/{id}/{approval}")
	public ResponseEntity<Donor> deleteDonorByApproval(@PathVariable Long id,@PathVariable String approval){
		logger.warn("deleting donor whose approval is pending with donor name");
		int d=donorRepo.deleteByApprovalAndName(id,approval);
		if(d==0) {
			throw new UserNotFoundException("Id- "+id);
		}
		return new ResponseEntity<Donor>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/admin/deleteAllRejected")
	public ResponseEntity<Donor> deleteAllDonorApproval(){
		logger.warn("deleting all donors whose approval is pending");
		int d= donorRepo.deleteByApproval("rejected");
		if(d==0) {
			throw new UserNotFoundException("Deleted all donors whose approval is rejected");
		}
		return new ResponseEntity<Donor>(HttpStatus.OK);
	}
	
}

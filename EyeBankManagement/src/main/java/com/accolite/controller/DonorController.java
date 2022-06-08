package com.accolite.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.accolite.entity.Donor;
import com.accolite.repository.DonorRepo;
import com.accolite.exception.UserNotFoundException;

@RestController
public class DonorController {
	private static Logger logger=LoggerFactory.getLogger(DonorController.class);
	
	@Autowired
	private DonorRepo donorRepo;
	
	/*
	 * @PostMapping("/registerdonor") public ResponseEntity<Donor>
	 * registerDonor(@RequestBody Donor donor){
	 * 
	 * 
	 * logger.info("registering donor"); donorRepo.save(donor); return new
	 * ResponseEntity<Donor>(donor,HttpStatus.CREATED); }
	 */
	
	
	@PostMapping("/registerdonor")
	public ResponseEntity<String> registerDonor(@RequestBody Donor donor)
	
	{
		Donor de= donor;
		if(!(de.getName().matches("[a-zA-Z\\s]{3,}"))) {
			logger.error("FirstName does not meet standards ");
			return new ResponseEntity<String>("First Name should be minimum 3 Characters only alphabets with spaces are allowed.",HttpStatus.NOT_ACCEPTABLE);
		}
		if(!(String.valueOf(de.getMobile()).matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}"))) {
			logger.error("Phone No. does not meet standards ");
			return new ResponseEntity<String>("Phone number should be a min of 10 digits",HttpStatus.NOT_ACCEPTABLE);
		}
		if(!(de.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"))) {
			logger.error("Entered the wrong email id");
			return new ResponseEntity<String>("Entered email is invalid, Please enter the corret Email Id", HttpStatus.NOT_ACCEPTABLE);
			
	}
		donorRepo.save(donor);	
		logger.info("Added the Donor record");
		//return ResponseEntity.status(HttpStatus.CREATED).build();
		//String successMessage = environment.getProperty("API.INSERT_SUCCESS");
		return new ResponseEntity<String>("Registered Successfully your Id is "+donor.getId(), HttpStatus.CREATED);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/getDonors")
	public ResponseEntity<List<Donor>> getAllDonors(){
		logger.info("fetching all donor details");
		List<Donor> donor=donorRepo.findAll();
		return new ResponseEntity<List<Donor>>(donor,HttpStatus.OK);
	}
	

	@GetMapping("/location/{location}")
	public ResponseEntity<List<Donor>> getDonorByLocation(@PathVariable String location){
		logger.info("fetching donor based on location");
		List<Donor> donor=donorRepo.findByLocation(location);
		return new ResponseEntity<List<Donor>>(donor,HttpStatus.FOUND);
	}
	
	@GetMapping("/bgroup/{bloodGroup}")
	public ResponseEntity<List<Donor>> getDonorByBloodGroup(@PathVariable String bloodGroup){
		logger.info("fetching donor details based on blood group");
		List<Donor> donor=donorRepo.findByBloodGroup(bloodGroup);
		return new ResponseEntity<List<Donor>>(donor,HttpStatus.FOUND);
	}
	
	@GetMapping("/donor/age/{from}/{to}")
	public ResponseEntity<List<Donor>> getDonorByAgeBetween(@PathVariable int from, @PathVariable int to){
		logger.info("fetching details of all donors b");
		List<Donor> donor=donorRepo.findByAgeBetween(from,to);
		if(donor.isEmpty()) {
			throw new UserNotFoundException("No user found between the selected criteria");
		}
		return new ResponseEntity<List<Donor>>(donor,HttpStatus.FOUND);
	}
	
	
}


/*  
 * registerdonor: { "name":"vanitha", "gender":"female",
 * "bloodGroup":"bnegative", "location":"bangalore", "mobile":"32740923",
 * "age":"27", "approval":false, "medicalHistory":"eye" }
 */




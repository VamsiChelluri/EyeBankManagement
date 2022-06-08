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
import com.accolite.entity.Recipient;
import com.accolite.exception.UserNotFoundException;
import com.accolite.repository.DonorRepo;
import com.accolite.repository.RecipientRepo;

@RestController
public class RecipientController {
	
	@Autowired
	private RecipientRepo recipientRepo;
	
	private static Logger logger=LoggerFactory.getLogger(DonorController.class);
	

	
	/*
	 * @PostMapping("/addRecipient") public ResponseEntity<Recipient>
	 * registerRecipient(@RequestBody Recipient recipient){
	 * logger.info("registering donor"); recipientRepo.save(recipient); return new
	 * ResponseEntity<Recipient>(recipient,HttpStatus.CREATED); }
	 */
	
	
	
	@PostMapping("/addRecipient")
	public ResponseEntity<String> registerRecipient(@RequestBody Recipient recipient)
	
	{
		Recipient re= recipient;
		if(!(re.getName().matches("[a-zA-Z\\s]{3,}"))) {
			logger.error("FirstName does not meet standards ");
			return new ResponseEntity<String>("First Name should be minimum 3 Characters only alphabets with spaces are allowed.",HttpStatus.NOT_ACCEPTABLE);
		}
		if(!(String.valueOf(re.getMobile()).matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}"))) {
			logger.error("Phone No. does not meet standards ");
			return new ResponseEntity<String>("Phone number should be a min of 10 digits",HttpStatus.NOT_ACCEPTABLE);
		}
		if(!(re.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"))) {
			logger.error("Entered the wrong email id");
			return new ResponseEntity<String>("Entered email is invalid, Please enter the corret Email Id", HttpStatus.NOT_ACCEPTABLE);
			
	}
		recipientRepo.save(recipient);	
		logger.info("Added the Recipient record");
		//return ResponseEntity.status(HttpStatus.CREATED).build();
		//String successMessage = environment.getProperty("API.INSERT_SUCCESS");
		return new ResponseEntity<String>("Registered Successfully your Id is "+recipient.getId(), HttpStatus.CREATED);
		
	}


	
	
	
	
	
	
	@GetMapping("/recipient/age/{from}/{to}")
	public ResponseEntity<List<Recipient>> getRecipientByAgeBetween(@PathVariable int from, @PathVariable int to){
		logger.info("fetching details of all donors b");
		List<Recipient> donor=recipientRepo.findByAgeBetween(from,to);
		if(donor.isEmpty()) {
			throw new UserNotFoundException("No user found between the selected criteria");
		}
		return new ResponseEntity<List<Recipient>>(donor,HttpStatus.FOUND);
	}

}


/*
 * registerRecipient: { "name":"vanitha", "gender":"female",
 * "bloodGroup":"bnegative", "location":"bangalore", "mobile":"32740923",
 * "age":"27", "approval":false, "medicalHistory":"eye" }
 */
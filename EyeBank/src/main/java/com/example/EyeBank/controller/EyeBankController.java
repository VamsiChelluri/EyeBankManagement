package com.example.EyeBank.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.EyeBank.entities.DonorEntity;
import com.example.EyeBank.entities.RecipientEnitity;
import com.example.EyeBank.exception.UserNotFoundException;
import com.example.EyeBank.repository.DonorRepository;
import com.example.EyeBank.repository.RecipientRepository;

@Transactional
@RestController
public class EyeBankController {

	private static final Logger logobj=LoggerFactory.getLogger(EyeBankController.class);
	@Autowired
	DonorRepository dRepo;
	@Autowired
	RecipientRepository rRepo;
	@Autowired
	Environment environment;
	@PostMapping("/donorregistration")
	public ResponseEntity<String> addDonor(@RequestBody DonorEntity donor)
	
	{
		DonorEntity de= donor;
		if(!(de.getFirstName().matches("[a-zA-Z\\s]{3,}"))) {
			logobj.error("FirstName does not meet standards ");
			return new ResponseEntity<String>("First Name should be minimum 3 Characters only alphabets with spaces are allowed.",HttpStatus.NOT_ACCEPTABLE);
		}
		if(!(String.valueOf(de.getPhoneNo()).matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}"))) {
			logobj.error("Phone No. does not meet standards ");
			return new ResponseEntity<String>("Phone number should be a min of 10 digits",HttpStatus.NOT_ACCEPTABLE);
		}
		if(!(de.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"))) {
			logobj.error("Entered the wrong email id");
			return new ResponseEntity<String>("Entered email is invalid, Please enter the corret Email Id", HttpStatus.NOT_ACCEPTABLE);
			
	}
		dRepo.save(donor);	
		logobj.info("Added the Donor record");
		//return ResponseEntity.status(HttpStatus.CREATED).build();
		//String successMessage = environment.getProperty("API.INSERT_SUCCESS");
		return new ResponseEntity<String>("Registered Successfully your Id is "+donor.getId(), HttpStatus.CREATED);
		
	}
	@PostMapping("/recipientregistration")
	public ResponseEntity<String> addRecipient(@RequestBody RecipientEnitity recipient)
	
	{
		//RecipientEnitity recObj=new RecipientEnitity();
		rRepo.save(recipient);	
		logobj.info("Added the Recipient record");
		//return ResponseEntity.status(HttpStatus.CREATED).build();
		//String successMessage = environment.getProperty("API.INSERT_SUCCESS");
		return new ResponseEntity<String>("Registered Successfully your Id is "+recipient.getId(), HttpStatus.CREATED);
		
	}
	@GetMapping("/admin/allrecipients")
	public List<RecipientEnitity> getAllRecipients()
	{
		logobj.warn("Returning all Recipients details by admin");
		return rRepo.findAll();
	}
	@GetMapping("/admin/alldonors")
	public List<DonorEntity> getAllDonors()
	{
		logobj.warn("Returning all Donors details by admin");
		return dRepo.findAll();
	}
	@GetMapping("/admin/name")
	public List<Object> donorDetails()
	{
		logobj.warn("Returning all Donors details by admin");
		return dRepo.getNameAndStatus();
	}
	/*@PutMapping("/admin/updatestatus")
	public RecipientEnitity updateStatus(@RequestBody RecipientEnitity recipient)
	
	{
			
		logobj.info("Updated the status of the Recipient record");
		//return ResponseEntity.status(HttpStatus.CREATED).build();
		//String successMessage = environment.getProperty("API.INSERT_SUCCESS");
		return rRepo.save(recipient);
		
	}*/
	@PutMapping("/admin/closereq/{id}")
	public ResponseEntity<RecipientEnitity> updatereq(@PathVariable long id)
	{
		long rr = rRepo.setForClosure("Closed", id);
		if(rr==0) {
			throw new UserNotFoundException("Id- "+id);
		}
		logobj.info("Closed the record");
		return ResponseEntity.status(HttpStatus.OK).build();
		
	}
	@DeleteMapping("/admin/delnotapproved")
	public ResponseEntity<String> deleteDon(){
		dRepo.deleteByStatus("Rejected");
		String successMessage = environment.getProperty("deletedMessage");
		return new ResponseEntity<String>(successMessage, HttpStatus.OK);
	}
	@GetMapping("/donor/filterbydemography/{ageFrom}/{toAge}")
	public List<DonorEntity> donorsAgeBetween(@PathVariable int ageFrom, @PathVariable int toAge ) {
		List<DonorEntity> de=dRepo.findByAgeBetween(ageFrom, toAge);
		if(de.isEmpty()) {
			throw new UserNotFoundException("No user found between the selected criteria");
		}
		return de;
	}
	@GetMapping("/recipient/filterbydemography/{ageFrom}/{toAge}")
	public List<RecipientEnitity> recipientsAgeBetween(@PathVariable int ageFrom, @PathVariable int toAge ) {
		List<RecipientEnitity> re= rRepo.findByAgeBetween(ageFrom, toAge);
		if(re.isEmpty()) {
			throw new UserNotFoundException("No user found between the selected criteria");
		}
		return re;
	}
	/*@GetMapping("/registered")
	public ResponseEntity<List<DonorEntity>> getDonorBasedOnRegistration(){
		logobj.info("fetching donor details who are registered");
		List<DonorEntity> donor=dRepo.getDonorsByRegistration();
		return new ResponseEntity<List<Donor>>(donor,HttpStatus.FOUND);
	}*/
	
	@PutMapping("/physician/approval/{id}/{approval}")
	public ResponseEntity<DonorEntity> updateDonorStatus(@PathVariable long id,@PathVariable String approval){
		logobj.warn("updating donor approval status");
		
		DonorEntity de=dRepo.updateApprovalBasedOnName(id,approval);
		if(de==null) {
			throw new UserNotFoundException("Id- "+id);
		}
		return new ResponseEntity<DonorEntity>(de,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/physician/additionalinfo/{id}/{extrainfo}")
	public ResponseEntity<DonorEntity> getExtraInfo(@PathVariable long id,@PathVariable String extraInfo){
		logobj.info("updating extra info in donor");
		DonorEntity de=dRepo.updateExtraInfo(id,extraInfo);
		if(de==null) {
			throw new UserNotFoundException("Id- "+id);
		}
		return new ResponseEntity<DonorEntity>(de,HttpStatus.ACCEPTED);
	}
}

package com.example.EyeBank.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="recipient_entity")
public class RecipientEnitity {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private long phoneNo;
	private String medicalHistory;
	@Column(columnDefinition = "varchar(255) default 'Open'")
	private String status;
	private String address;

	public RecipientEnitity() {
		super();
	}
	public RecipientEnitity(String firstName, String lastName, int age, String gender, long phoneNo,
			String medicalHistory, String status, String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.phoneNo = phoneNo;
		this.medicalHistory = medicalHistory;
		this.status = status;
		this.address = address;
	}
	@Override
	public String toString() {
		return "RecipientEnitity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", gender=" + gender + ", phoneNo=" + phoneNo + ", medicalHistory=" + medicalHistory + ", status="
				+ status + ", address=" + address + "]";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public long getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getMedicalHistory() {
		return medicalHistory;
	}
	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	
	
}

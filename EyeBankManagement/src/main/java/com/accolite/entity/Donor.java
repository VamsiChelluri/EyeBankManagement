package com.accolite.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Donor {
	@Id
	@SequenceGenerator(
			name="donor_sequence",
			sequenceName="donor_sequence",
			allocationSize=1
			)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "donor_sequence")
	private Long id;
	@Size(min = 3,max = 15)
	private String name;
	private String gender;
	private String bloodGroup;
	private String location;
	@Column(length = 10)
	private Long mobile;
	private int age;
	@Column(columnDefinition = "varchar(255) default 'Submitted for approval'")
	private String approval;
	private String medicalHistory;
	
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
	private String email;
	
	private String extraInfo;

}

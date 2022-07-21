package com.example.EyeBank.exception;

import java.util.Date;

public class ExceptionResponse {

	private String message;
	private Date date;
	private String details;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public ExceptionResponse(String message, Date date, String details) {
		super();
		this.message = message;
		this.date = date;
		this.details = details;
	}
	public ExceptionResponse() {
		super();
	}
	
}

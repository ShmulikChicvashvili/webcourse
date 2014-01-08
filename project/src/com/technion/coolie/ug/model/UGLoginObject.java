package com.technion.coolie.ug.model;

public class UGLoginObject {
	
	String studentId;
	String password;
	
	public UGLoginObject(String studentId, String password) {
		super();
		this.studentId = studentId;
		this.password = password;
	}
	
	public String getStudentId() {
		return studentId;
	}
	public String getPassword() {
		return password;
	}
	
	
	
	
}

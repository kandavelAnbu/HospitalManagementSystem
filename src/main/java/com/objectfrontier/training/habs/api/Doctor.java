package com.objectfrontier.training.habs.api;

public class Doctor {

	public Doctor(String name,String specialization,String department) {
		this.name 			= name;
		this.specialization = specialization;
		this.department 	= department;
	}

	public Doctor() {
		
	}
	
	public Doctor(long id,String name,String specialization,String department, String name1) {
		this.name 			= name;
		this.specialization = specialization;
		this.department 	= department;
		this.name1 			= name1;
	}

	public String name;
	public String specialization;
	public String department;
	public long hid;
	public String name1;
	public long id;
}

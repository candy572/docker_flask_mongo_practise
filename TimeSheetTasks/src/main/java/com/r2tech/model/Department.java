package com.r2tech.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Department {
    
	@Id
	private int dept_number;
	private String dept_name;
	
	public Department() {
		
	}

	public Department(int dept_number, String dept_name) {
		super();
		this.dept_number = dept_number;
		this.dept_name = dept_name;
	}

	public int getDept_number() {
		return dept_number;
	}

	public void setDept_number(int dept_number) {
		this.dept_number = dept_number;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	@Override
	public String toString() {
		return "Department [dept_number=" + dept_number + ", dept_name=" + dept_name + "]";
	}
	
}

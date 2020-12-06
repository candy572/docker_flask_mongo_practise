package com.r2tech.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Entity
@Component
public class Employee {
	
	@Id
	private int emp_id;
	private String emp_name;
	private String phone;
	private String mail;
	private int dept_number;	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date join_date;
	private int salary;
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(int emp_id, String emp_name, String phone, String mail, int dept_number, Date join_date,
			int salary) {
		super();
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.phone = phone;
		this.mail = mail;
		this.dept_number = dept_number;
		this.join_date = join_date;
		this.salary = salary;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getDept_number() {
		return dept_number;
	}

	public void setDept_number(int dept_number) {
		this.dept_number = dept_number;
	}

	public Date getJoin_date() {
		return join_date;
	}

	public void setJoin_date(Date join_date) {
		this.join_date = join_date;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [emp_id=" + emp_id + ", emp_name=" + emp_name + ", phone=" + phone + ", mail=" + mail
				+ ", dept_number=" + dept_number + ", join_date=" + join_date + ", salary=" + salary + "]";
	}	
}

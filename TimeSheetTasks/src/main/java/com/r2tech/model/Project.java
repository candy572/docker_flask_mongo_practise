package com.r2tech.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
@Entity
public class Project {
	
	@Id
	private int proj_id;
	private String proj_type;
	private String proj_name;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date start_date;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date end_date;
	private int proj_size;
	private String manager;
	
	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Project(int proj_id, String proj_type, String proj_name, Date start_date, Date end_date, int proj_size,
			String manager) {
		super();
		this.proj_id = proj_id;
		this.proj_type = proj_type;
		this.proj_name = proj_name;
		this.start_date = start_date;
		this.end_date = end_date;
		this.proj_size = proj_size;
		this.manager = manager;
	}
	public int getProj_id() {
		return proj_id;
	}
	public void setProj_id(int proj_id) {
		this.proj_id = proj_id;
	}
	public String getProj_type() {
		return proj_type;
	}
	public void setProj_type(String proj_type) {
		this.proj_type = proj_type;
	}
	public String getProj_name() {
		return proj_name;
	}
	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public int getProj_size() {
		return proj_size;
	}
	public void setProj_size(int proj_size) {
		this.proj_size = proj_size;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	@Override
	public String toString() {
		return "Project [proj_id=" + proj_id + ", proj_type=" + proj_type + ", proj_name=" + proj_name + ", start_date="
				+ start_date + ", end_date=" + end_date + ", proj_size=" + proj_size + ", manager=" + manager + "]";
	}
}

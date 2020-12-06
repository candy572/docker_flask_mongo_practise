package com.r2tech.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="timesheet")
public class TimeSheet {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int tsheet_id;
		private int emp_id;
		private int proj_id;
		@Temporal(TemporalType.DATE)
		@DateTimeFormat(pattern = "yyyy-mm-dd")
		private Date submission_date;
		@Temporal(TemporalType.DATE)
		@DateTimeFormat(pattern = "yyyy-mm-dd")
		private Date create_date;
		@Temporal(TemporalType.DATE)
		@DateTimeFormat(pattern = "yyyy-mm-dd")
		private Date last_modified;
		private int day_hours;
		private int extra_hours;
		
		
		public TimeSheet() {
		}


		public TimeSheet(int tsheet_id, int emp_id, int proj_id, Date submission_date, Date create_date,
				Date last_modified, int day_hours, int extra_hours) {
			super();
			this.tsheet_id = tsheet_id;
			this.emp_id = emp_id;
			this.proj_id = proj_id;
			this.submission_date = submission_date;
			this.create_date = create_date;
			this.last_modified = last_modified;
			this.day_hours = day_hours;
			this.extra_hours = extra_hours;
		}


		public int getTsheet_id() {
			return tsheet_id;
		}


		public void setTsheet_id(int tsheet_id) {
			this.tsheet_id = tsheet_id;
		}


		public int getEmp_id() {
			return emp_id;
		}


		public void setEmp_id(int emp_id) {
			this.emp_id = emp_id;
		}


		public int getProj_id() {
			return proj_id;
		}


		public void setProj_id(int proj_id) {
			this.proj_id = proj_id;
		}


		public Date getSubmission_date() {
			return submission_date;
		}


		public void setSubmission_date(Date submission_date) {
			this.submission_date = submission_date;
		}


		public Date getCreate_date() {
			return create_date;
		}


		public void setCreate_date(Date create_date) {
			this.create_date = create_date;
		}


		public Date getLast_modified() {
			return last_modified;
		}


		public void setLast_modified(Date last_modified) {
			this.last_modified = last_modified;
		}


		public int getDay_hours() {
			return day_hours;
		}


		public void setDay_hours(int day_hours) {
			this.day_hours = day_hours;
		}


		public int getExtra_hours() {
			return extra_hours;
		}


		public void setExtra_hours(int extra_hours) {
			this.extra_hours = extra_hours;
		}


		@Override
		public String toString() {
			return "TimeSheet [tsheet_id=" + tsheet_id + ", emp_id=" + emp_id + ", proj_id=" + proj_id
					+ ", submission_date=" + submission_date + ", create_date=" + create_date + ", last_modified="
					+ last_modified + ", day_hours=" + day_hours + ", extra_hours=" + extra_hours + "]";
		}
		
		

}

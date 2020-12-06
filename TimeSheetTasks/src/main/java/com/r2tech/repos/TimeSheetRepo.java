package com.r2tech.repos;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.r2tech.model.TimeSheet;

public interface TimeSheetRepo extends JpaRepository<TimeSheet, Integer> {

	@Query("from TimeSheet t where t.emp_id= :emp_id and t.submission_date= :submission_date")
	TimeSheet getTimeSheetBySubmissionDate(@Param("emp_id") int emp_id, 
					    				   @Param("submission_date") Date submission_date);
	
	@Query("from TimeSheet t where t.emp_id= :emp_id and t.proj_id= :proj_id")
	List<TimeSheet> getTimeSheetsByEmployeeProjects(@Param("emp_id") int emp_id,
													@Param("proj_id") int proj_id);
	
	@Query("from TimeSheet t where t.emp_id= :emp_id")
	List<TimeSheet> getAllEmployeeTimeSheets(@Param("emp_id") int emp_id);
	
	@Query("from TimeSheet t where t.proj_id= :proj_id")
	List<TimeSheet> getAllProjectTimeSheets(@Param("proj_id") int proj_id);
	
	@Query("from TimeSheet t where t.emp_id= :emp_id and t.submission_date>= :start_date and t.submission_date<= :end_date")
	List<TimeSheet> getAllEmpTimeSheetsByDateRange(@Param("emp_id") int emp_id,
												   @Param("start_date") Date start_date,
												   @Param("end_date") Date end_date);	
	
	@Query("from TimeSheet t where t.proj_id= :proj_id and t.submission_date>= :start_date and t.submission_date<= :end_date")
	List<TimeSheet> getAllProjectTimeSheetsByDateRange(@Param("proj_id") int emp_id,
													   @Param("start_date") Date start_date,
													   @Param("end_date") Date end_date);	

	@Query("from TimeSheet t where t.emp_id= :emp_id and t.proj_id= :proj_id and t.submission_date>= :start_date and t.submission_date<= :end_date")
	List<TimeSheet> getAllEmployeeProjectTimeSheetsByDateRange(@Param("emp_id") int emp_id,
															   @Param("proj_id") int proj_id,
															   @Param("start_date") Date start_date,
															   @Param("end_date") Date end_date);
}

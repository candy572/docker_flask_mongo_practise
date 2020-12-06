package com.r2tech.web;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.r2tech.model.Project;
import com.r2tech.model.TimeSheet;
import com.r2tech.repos.EmployeeRepo;
import com.r2tech.repos.ProjectRepo;
import com.r2tech.repos.TimeSheetRepo;
import com.r2tech.validations.ProjectValidations;

@RestController
@RequestMapping("timesheet")
public class TimeSheetController {

	@Autowired
	TimeSheetRepo tsheetrepo;

	@Autowired
	ProjectRepo projrepo;

	@Autowired
	EmployeeRepo emprepo;

	@Autowired
	ProjectValidations projval;

	List<TimeSheet> tlist;

	@RequestMapping(path = "/", produces = "application/json")
	public List<TimeSheet> testTimeSheets() {
		return Arrays.asList(new TimeSheet());
	}

	@PostMapping(path = "add", consumes = "application/json")
	public ResponseEntity<?> addTimeSheet(@RequestBody TimeSheet tsheet) {

		/*
		 * curl --location --request POST 'localhost:8080/timesheet/add' \ --header
		 * 'Accept: application/json' \ --header 'Content-Type: application/json' \
		 * --data-raw '{ "emp_id" : 7, "proj_id": 4, "submission_date": "2020-11-26",
		 * "create_date": "2020-05-13", "last_modified": "2020-05-13", "day_hours":6,
		 * "extra_hours":3 }'
		 */

		Project proj = projrepo.findById(tsheet.getProj_id()).orElse(new Project());
		Project weekend_proj = projrepo.getProjectByName("weekend_work");

		if (!emprepo.existsById(tsheet.getEmp_id()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("employee %d does not exist", tsheet.getEmp_id()));
		else if (proj.getProj_id() == 0)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("project %d does not exist", tsheet.getProj_id()));
		else if (tsheet.getSubmission_date().compareTo(proj.getStart_date()) < 0
				|| tsheet.getSubmission_date().compareTo(proj.getEnd_date()) > 0)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Submission date should be after project start date before project end date.");
		else if (tsheet.getDay_hours() > 8)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("cannot submit more than 8 hours for regular work.");
		else if (tsheet.getExtra_hours() > 4)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("cannot submit more than 4 hours for extra work.");
		else if (tsheet.getSubmission_date().compareTo(new Date()) >= 1)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Not allowed to submit the timesheet for future date.");
		else if (tsheetrepo.getTimeSheetBySubmissionDate(tsheet.getEmp_id(), tsheet.getSubmission_date()) != null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Timesheet already submitted to current date to current employee. Use Update mode.");
		else if ((projval.isWeekEnd(tsheet.getSubmission_date())) && (proj.getProj_id() != weekend_proj.getProj_id()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("Weekend project id should be %d.", weekend_proj.getProj_id()));
		else if ((!projval.isWeekEnd(tsheet.getSubmission_date())) && (proj.getProj_id() == weekend_proj.getProj_id()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("Not allowed to have weekend project code %d in week day.", weekend_proj.getProj_id()));

		tsheet.setCreate_date(new Date());

		tsheetrepo.save(tsheet);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("TimeSheetId:" + tsheet.getTsheet_id());
	}

	@GetMapping(path = "/empproj/{eid}/{pid}", produces = "application/json")
	public List<TimeSheet> getTimeSheetByEmpIdAndProjectId(@PathVariable("eid") int eid, @PathVariable("pid") int pid) {

		/* curl --location --request GET 'localhost:8080/timesheet/empproj/1234/999' */

		tlist = tsheetrepo.getTimeSheetsByEmployeeProjects(eid, pid);
		ResponseEntity.status(HttpStatus.ACCEPTED);
		return tlist;
	}

	@GetMapping(path = "/employee/{eid}", produces = "application/json")
	public List<TimeSheet> getEmployeeTimeSheets(@PathVariable("eid") int eid) {

		/* curl --location --request GET 'localhost:8080/timesheet/employee/1234' */

		tlist = tsheetrepo.getAllEmployeeTimeSheets(eid);
		ResponseEntity.status(HttpStatus.ACCEPTED);
		return tlist;
	}

	@GetMapping(path = "/project/{pid}", produces = "application/json")
	public List<TimeSheet> getProjectTimeSheets(@PathVariable("pid") int pid) {

		/* curl --location --request GET 'localhost:8080/timesheet/project/999' \ */

		tlist = tsheetrepo.getAllProjectTimeSheets(pid);
		ResponseEntity.status(HttpStatus.ACCEPTED);
		return tlist;
	}

	@GetMapping(path = "/employee/date_range/", produces = "application/json", consumes = "application/json")
	public List<TimeSheet> getEmpDateRange(@RequestBody JsonNode payload) throws Exception {

		/*
		 * curl --location --request GET 'localhost:8080/timesheet/employee/date_range/'
		 * \ --header 'Accept: application/json' \ --header 'Content-Type:
		 * application/json' \ --data-raw '{ "emp_id": 5, "start_date" : "2010-01-01",
		 * "end_date" : "2040-12-31" }'
		 */

		int eid = payload.get("emp_id").asInt();
		String strt_date = payload.get("start_date").asText();
		String final_date = payload.get("end_date").asText();

		tlist = tsheetrepo.getAllEmpTimeSheetsByDateRange(eid, 
														  new SimpleDateFormat("yyyy-MM-dd").parse(strt_date),
														  new SimpleDateFormat("yyyy-MM-dd").parse(final_date));
		return tlist;
	}

	@GetMapping(path = "/project/date_range/", produces = "application/json", consumes = "application/json")
	public List<TimeSheet> getProjectDateRange(@RequestBody JsonNode payload) throws Exception {

		/*
		 * curl --location --request GET 'localhost:8080/timesheet/project/date_range/'
		 * \ --header 'Accept: application/json' \ --header 'Content-Type:
		 * application/json' \ --data-raw '{ "proj_id": 999, "start_date" :
		 * "2000-01-01", "end_date" : "2040-12-31" }'
		 */

		int proj_id = payload.get("proj_id").asInt();
		String strt_date = payload.get("start_date").asText();
		String final_date = payload.get("end_date").asText();

		tlist = tsheetrepo.getAllProjectTimeSheetsByDateRange(proj_id,
															  new SimpleDateFormat("yyyy-MM-dd").parse(strt_date),
															  new SimpleDateFormat("yyyy-MM-dd").parse(final_date));
		return tlist;
	}

	@GetMapping(path = "/employee/project/date_range/", produces = "application/json", consumes = "application/json")
	public List<TimeSheet> getAllProjectTimeSheetsByEmployee(@RequestBody JsonNode payload) throws Exception {

		/*
		 * curl --location --request GET
		 * 'localhost:8080/timesheet/employee/project/date_range/' \ --header 'Accept:
		 * application/json' \ --header 'Content-Type: application/json' \ --data-raw '{
		 * "proj_id": 999, "emp_id":7, "start_date" : "2000-01-01", "end_date" :
		 * "2040-12-31" }'
		 */

		int emp_id = payload.get("emp_id").asInt();
		int proj_id = payload.get("proj_id").asInt();
		String strt_date = payload.get("start_date").asText();
		String final_date = payload.get("end_date").asText();
		
    	tlist = tsheetrepo.getAllEmployeeProjectTimeSheetsByDateRange(emp_id,proj_id, 
		        													  new SimpleDateFormat("yyyy-MM-dd").parse(strt_date), 
				 													  new SimpleDateFormat("yyyy-MM-dd").parse(final_date));

		return tlist;
	}

	@RequestMapping(path = "delete", consumes = "application/json")
	public ResponseEntity<?> deleteTimeSheet(@RequestBody JsonNode payload) throws Exception {

		/*
		 * curl --location --request GET 'localhost:8080/timesheet/delete' \ --header
		 * 'Content-Type: application/json' \ --data-raw '{ "emp_id" : 7,
		 * "submission_date" : "2020-11-28" }'
		 */		
		
		int emp_id = payload.get("emp_id").asInt();
		String submit_date = payload.get("submission_date").asText();

		TimeSheet tsheet = tsheetrepo.getTimeSheetBySubmissionDate(emp_id,
																  new SimpleDateFormat("yyyy-MM-dd").parse(submit_date));

		if(tsheet == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Timesheet not submitted by employee in the given date.");
		
		tsheetrepo.deleteById(tsheet.getTsheet_id());
		return ResponseEntity.status(HttpStatus.OK).body("deleted");
		
	}
	
	@PostMapping(path="update", consumes = "application/json")
	public ResponseEntity<?> updateTimeSheet(@RequestBody TimeSheet tsheet){
		
		/*
		 * curl --location --request POST 'localhost:8080/timesheet/update' \ --header
		 * 'Content-Type: application/json' \ --header 'Accept: application/json' \
		 * --data-raw '{ "emp_id" : 7, "submission_date" : "2020-11-27", "proj_id" : 2,
		 * "day_hours" : 5, "extra_hours" : 1 }'
		 */		
		
		TimeSheet dbtsheet = tsheetrepo.getTimeSheetBySubmissionDate(tsheet.getEmp_id(),tsheet.getSubmission_date());
		Project proj = projrepo.findById(tsheet.getProj_id()).orElse(new Project());
		Project weekend_proj = projrepo.getProjectByName("weekend_work");
		
		if(dbtsheet == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Employee did not submit the timesheet for the given date.Use create mode first.");		
		else if (proj.getProj_id() == 0)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("project %d does not exist", tsheet.getProj_id()));
		else if (tsheet.getSubmission_date().compareTo(proj.getStart_date()) < 0 || tsheet.getSubmission_date().compareTo(proj.getEnd_date()) > 0)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Submission date should be after project start date before project end date.");
		else if (tsheet.getDay_hours() > 8)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("cannot submit more than 8 hours for regular work.");
		else if (tsheet.getExtra_hours() > 4)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("cannot submit more than 4 hours for extra work.");
		else if (tsheet.getSubmission_date().compareTo(new Date()) >= 1)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Not allowed to submit the timesheet for future date.");
		else if ((projval.isWeekEnd(tsheet.getSubmission_date())) && (proj.getProj_id() != weekend_proj.getProj_id()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("Weekend project id should be %d.", weekend_proj.getProj_id()));
		else if ((!projval.isWeekEnd(tsheet.getSubmission_date())) && (proj.getProj_id() == weekend_proj.getProj_id()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("Not allowed to have weekend project code %d in week day.", weekend_proj.getProj_id()));
		
		tsheet.setLast_modified(new Date());
		tsheet.setCreate_date(dbtsheet.getCreate_date());		
		tsheet.setTsheet_id(dbtsheet.getTsheet_id());
		tsheetrepo.save(tsheet);
		return ResponseEntity.status(HttpStatus.OK).body("updated");
		
	}

}

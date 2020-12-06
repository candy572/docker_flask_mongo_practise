package com.r2tech.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2tech.model.Project;
import com.r2tech.repos.ProjectRepo;

@RestController
@RequestMapping("project")
public class ProjectController {

	@Autowired
	ProjectRepo projrepo;
	
	@GetMapping(path = "/", produces = "application/json")
	public List<Project> testProjects() {

		/*
		 * curl --location --request GET 'localhost:8080/project/' \ --header
		 * 'Content-Type: application/json' \ --header 'Accept: application/json' \
		 * --data-raw ''
		 */
		return Arrays.asList(new Project(1, "sample", "internal", null, null, 0, null));
	}
	
	@GetMapping(path = "/{projid}",produces="application/json")
	public Project getProject(@PathVariable("projid") int projid) {		
		/*
		 * curl --location --request GET 'localhost:8080/project/5' \ --header
		 * 'Content-Type: application/json' \ --header 'Accept: application/json' \
		 * --data-raw ''
		 */
		return projrepo.findById(projid).orElse(new Project());
	}

	@GetMapping(path = "all",produces="application/json")
	public List<Project> getAllProjects(){
		/*
		 * curl --location --request GET 'localhost:8080/project/all' \ --header
		 * 'Content-Type: application/json' \ --header 'Accept: application/json' \
		 * --data-raw ''
		 */		
		return projrepo.findAll();
	}
	
	@PostMapping(path="add",produces = "application/json",consumes = "application/json")
	public ResponseEntity<?> addEmployee(@RequestBody Project proj) {
		
		/*
		 * curl --location --request POST 'localhost:8080/project/add/' \ --header
		 * 'Content-Type: application/json' \ --header 'Accept: application/json' \
		 * --data-raw '{ "proj_id":2, "proj_type":"billing",
		 * "proj_name":"system integrations", "start_date":"2020-05-15",
		 * "end_date":"2021-07-15", "proj_size":5, "manager":"koundinya" }'
		 */		
		
		if(projrepo.existsById(proj.getProj_id())) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate project id.");		
		else if(proj.getEnd_date().compareTo(proj.getStart_date()) <= 0) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("end date should be after start date.");
		
		projrepo.save(proj);
		
		return ResponseEntity.ok("save successful");
	}
	
	@RequestMapping(path="delete/{projid}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("projid") int projid) {
		
//		curl --location --request GET 'localhost:8080/project/delete/2'
		
		if(!projrepo.existsById(projid)) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("project does not exist.");
		
		projrepo.deleteById(projid);
		return ResponseEntity.ok("delete successful");
	}
	
	@RequestMapping(path="update",consumes = "application/json")
	public ResponseEntity<?> updateEmployee(@RequestBody Project proj) {
		/*
		 * curl --location --request GET 'localhost:8080/project/update' \ --header
		 * 'Content-Type: application/json' \ --header 'Accept: application/json' \
		 * --data-raw ' { "proj_id":5, "proj_type":"internal",
		 * "proj_name":"transport management", "start_date":"2020-01-15",
		 * "end_date":"2022-11-15", "proj_size":95, "manager":"candy" } '
		 */		
		
		if(!projrepo.existsById(proj.getProj_id())) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("project does not exist.");
		else if(proj.getEnd_date().compareTo(proj.getStart_date()) <= 0) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("end date should be after start date.");
		
		projrepo.save(proj);
		return ResponseEntity.ok("update successful");
	}
	
}

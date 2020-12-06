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
import com.r2tech.model.Department;
import com.r2tech.repos.DepartmentRepo;

@RestController
@RequestMapping("department")
public class DepartmentController {

	@Autowired
	DepartmentRepo deptrepo;
	
	@RequestMapping(path="/",produces="application/json")	
	public List<Department> testDept(){
		
		/*
		 * curl --location --request GET 'localhost:8080/department/' \ --data-raw ''
		 */
		return Arrays.asList(new Department(123,"developemnt"));
	}
	
	@GetMapping(path="/all",produces="application/json")	
	public List<Department> getAllDepartments(){
		
		/*
		 * curl --location --request GET 'localhost:8080/department/all' \ --header
		 * 'Accept: application/json' \ --data-raw ''
		 */
		
		List<Department> deptList = deptrepo.findAll();
		return deptList;
	}
	
	@GetMapping(path="/{deptid}",produces="application/json")
	public Department getDepartment(@PathVariable("deptid") int deptid) {
		
		/*
		 * curl --location --request GET 'localhost:8080/department/8' \ --header
		 * 'Accept: application/json' \ --data-raw ''
		 */
		
		Department dept = deptrepo.findById(deptid).orElse(new Department());
		return dept;
	}
	
	@PostMapping(path="/add",consumes="application/json",produces="application/json")
	public ResponseEntity<?> addDepartment(@RequestBody Department dept) {
	    
		/*
		 * curl --location --request POST 'localhost:8080/department/add' \ --header
		 * 'Content-Type: application/json' \ --header 'Accept: application/json' \
		 * --data-raw '{ "dept_number":7, "dept_name": "fire safety" }'
		 */
		
		if(deptrepo.existsById(dept.getDept_number()))
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate department.");
		deptrepo.save(dept);
		return ResponseEntity.ok("save successful");
		
	}	
	
	@RequestMapping(path="/delete/{deptid}")
	public ResponseEntity<?> deleteDepartment(@PathVariable("deptid") int deptid) {
		
		/*
		 * curl --location --request GET 'localhost:8080/department/delete/8' \
		 * --data-raw ''
		 */
		
		if(!deptrepo.existsById(deptid))
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Department not found.");
		deptrepo.deleteById(deptid);
		return ResponseEntity.ok("deletion successful");		
	}
	
	@RequestMapping(path="update",consumes = "application/json")
	public ResponseEntity<?> updateDepartment(@RequestBody Department dept){
		
		/*
		 * curl --location --request GET 'localhost:8080/department/update' \ --header
		 * 'Content-Type: application/json' \ --data-raw '{ "dept_number":55,
		 * "dept_name": "Travel" }'
		 */		
		
		if(!deptrepo.existsById(dept.getDept_number()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Department not found.");
		
		deptrepo.save(dept);
		return ResponseEntity.ok("updation successful");
	}
	
}

package com.r2tech.web;

import java.util.Arrays;
import java.util.Date;
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

import com.r2tech.model.Employee;
import com.r2tech.repos.DepartmentRepo;
import com.r2tech.repos.EmployeeRepo;

@RestController
@RequestMapping("employee")
public class EmployeeController {

	@Autowired
	EmployeeRepo emprepo;
	
	@Autowired
	DepartmentRepo deptrepo;
	
	@RequestMapping(path = "/", produces = "application/json")
	public List<Employee> testEmps() {
		
		/*
		 * curl --location --request GET 'localhost:8080/employee/' \ --header
		 * 'Content-Type: application/json' \ --header 'Accept: application/json' \
		 * --data-raw ''
		 */
		
		return Arrays.asList(new Employee(572, "srinivas", "123", "sample@gamil.com", 4, new Date(),2345));
	}
	
	@GetMapping(path="/all",produces="application/json")
	public List<Employee> getAllEmployees(){
		/*
		 * curl --location --request GET 'localhost:8080/employee/all' \ --header
		 * 'Content-Type: application/json' \ --header 'Accept: application/json' \
		 * --data-raw ''
		 */
		
		List<Employee> emplist = emprepo.findAll();
		return emplist;
	}
	
	@GetMapping(path="/{empid}",produces="application/json")
	public Employee getEmployee(@PathVariable("empid") int empid){
		/*
		 * curl --location --request GET 'localhost:8080/employee/1234' \ --header
		 * 'Content-Type: application/json' \ --header 'Accept: application/json' \
		 * --data-raw ''
		 */
		return emprepo.findById(empid).orElse(new Employee());		
	}
	
	@PostMapping(path="/add",produces="application/json",consumes="application/json")
	public ResponseEntity<?> addEmployee(@RequestBody Employee emp) {
		
		/*
		 * curl --location --request POST 'localhost:8080/employee/add' \ --header
		 * 'Content-Type: application/json' \ --header 'Accept: application/json' \
		 * --data-raw '{
		 * 
		 * "emp_id" : 5, "emp_name" : "candy", "phone" : "3444", "mail" :
		 * "dummy@gmail.com", "dept_number" : 4, "join_date" : "2013-07-23", "salary" :
		 * 123444.56
		 * 
		 * }'
		 */		
		if(!deptrepo.existsById(emp.getDept_number()))
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Department does not exist.");
		else if(emp.getEmp_id() == 0) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Blank employee id not allowed.");
		else if(emprepo.existsById(emp.getEmp_id()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate employee id.");
		
		emprepo.save(emp);
		return ResponseEntity.ok("save successful");
	}
	
	@RequestMapping(path="/delete/{empid}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("empid") int empid) {
		/*
		 * curl --location --request GET 'localhost:8080/employee/delete/5' \ --header
		 * 'Content-Type: application/json' \ --header 'Accept: application/json' \
		 * --data-raw ''
		 */
		
		if(!emprepo.existsById(empid)) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("employee does not exist.");
		
		emprepo.deleteById(empid);
		return ResponseEntity.ok("deleted successfully");
	}
	
	@PostMapping(path="update",consumes = "application/json",produces = "application/json")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee emp) {
		
		/*
		 * curl --location --request POST 'localhost:8080/employee/update/' \ --header
		 * 'Content-Type: application/json' \ --header 'Accept: application/json' \
		 * --data-raw '{ "emp_id" : 7, "emp_name" : "candyyyy", "phone" : "344477",
		 * "mail" : "dummy@gmail.com", "dept_number" : 4, "join_date" : "2018-11-23",
		 * "salary" : 234.56 }'
		 */		
		Employee lObjEmp;
		
		if(!emprepo.existsById(emp.getEmp_id())) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("employee does not exist.");
		else {
			lObjEmp = emprepo.findById(emp.getEmp_id()).orElse(new Employee());
			if(emp.getDept_number() != lObjEmp.getDept_number()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			            .body("Not allowed to change the department.");
			}
		}
		
		emprepo.save(emp);
		return ResponseEntity.ok("updated successfully");
	}
	
	
}

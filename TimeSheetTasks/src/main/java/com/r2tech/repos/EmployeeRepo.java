package com.r2tech.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.r2tech.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

}

package com.r2tech.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.r2tech.model.Project;

@Transactional
public interface ProjectRepo extends JpaRepository<Project, Integer> {
	
	@Query("from Project p where p.proj_name=:proj_name")
	public Project getProjectByName(@Param("proj_name") String proj_name);

}

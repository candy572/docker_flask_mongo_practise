package com.r2tech.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.r2tech")
@EnableJpaRepositories("com.r2tech.repos")
@EntityScan("com.r2tech.model")
public class TimeSheetTasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeSheetTasksApplication.class, args);
	}

}

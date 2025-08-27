package com.example.WORKPLS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
public class WorkplsApplication {

	public static void main(String[] args) {

		SpringApplication.run(WorkplsApplication.class, args);
		System.out.println("Welcome to Workpls!");
	}

}

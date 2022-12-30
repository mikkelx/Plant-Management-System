package com.example.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PmsTmpApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmsTmpApplication.class, args);
	}

}

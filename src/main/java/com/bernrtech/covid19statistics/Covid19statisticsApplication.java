package com.bernrtech.covid19statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Covid19statisticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Covid19statisticsApplication.class, args);
	}

}

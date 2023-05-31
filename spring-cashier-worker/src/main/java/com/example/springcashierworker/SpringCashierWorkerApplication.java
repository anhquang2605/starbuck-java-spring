package com.example.springcashierworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringCashierWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCashierWorkerApplication.class, args);
	}

}

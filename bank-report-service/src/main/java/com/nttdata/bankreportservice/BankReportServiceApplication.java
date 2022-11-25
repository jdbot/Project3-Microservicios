package com.nttdata.bankreportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Main Class.
 */
@EnableEurekaClient
@SpringBootApplication
public class BankReportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankReportServiceApplication.class, args);
	}

}

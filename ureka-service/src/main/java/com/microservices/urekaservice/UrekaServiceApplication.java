package com.microservices.urekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class UrekaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrekaServiceApplication.class, args);
	}

}

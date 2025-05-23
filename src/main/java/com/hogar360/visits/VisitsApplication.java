package com.hogar360.visits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.hogar360")
public class VisitsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitsApplication.class, args);
	}

}

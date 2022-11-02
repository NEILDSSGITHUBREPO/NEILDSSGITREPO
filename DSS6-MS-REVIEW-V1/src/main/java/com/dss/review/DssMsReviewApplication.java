package com.dss.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DssMsReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(DssMsReviewApplication.class, args);
	}

}

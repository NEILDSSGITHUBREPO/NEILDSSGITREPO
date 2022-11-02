package com.dss.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DssMsMovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(DssMsMovieApplication.class, args);
	}

}

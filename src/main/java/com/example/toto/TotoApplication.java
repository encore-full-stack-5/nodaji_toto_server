package com.example.toto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TotoApplication.class, args);
	}

}

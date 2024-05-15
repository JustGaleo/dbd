package com.justgaleo.dbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.justgaleo.dbd"})
public class DbdApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbdApplication.class, args);
	}

}

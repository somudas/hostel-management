package com.example.hostelmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HostelManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HostelManagementApplication.class, args);
	}

}

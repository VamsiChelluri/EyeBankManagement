package com.example.EyeBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.EyeBank.securitymodels.UserRepository;


@SpringBootApplication
public class EyeBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(EyeBankApplication.class, args);
	}

}

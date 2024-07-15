package com.literalura.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.literalura.api.main.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {
	@Autowired
	private Main main;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws JsonProcessingException {
		main.showMenu();
	}
}
package com.example.njug_spring_crud_project;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class NjugSpringCrudProjectApplication {

	public static void main(String[] args) {
		var dotenv = Dotenv.load();
		// DB
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		// MAIL
		System.setProperty("MAIL_HOST", dotenv.get("MAIL_HOST"));
		System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
		System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));

		SpringApplication.run(NjugSpringCrudProjectApplication.class, args);
	}

}

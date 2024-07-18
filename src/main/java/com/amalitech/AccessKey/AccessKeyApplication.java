package com.amalitech.AccessKey;

import jakarta.annotation.PostConstruct;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccessKeyApplication {
	@PostConstruct
	public void init() {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("spring.mail.host", dotenv.get("MAIL_HOST"));
		System.setProperty("spring.mail.port", dotenv.get("MAIL_PORT"));
		System.setProperty("spring.mail.username", dotenv.get("MAIL_USERNAME"));
		System.setProperty("spring.mail.password", dotenv.get("MAIL_PASSWORD"));
	}

	public static void main(String[] args) {
		SpringApplication.run(AccessKeyApplication.class, args);
	}

}

package com.donation.annadanam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin("http://localhost:3000")
public class AnnadanamApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnnadanamApplication.class, args);
	}

}

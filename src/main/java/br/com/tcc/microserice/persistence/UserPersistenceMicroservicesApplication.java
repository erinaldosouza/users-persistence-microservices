package br.com.tcc.microserice.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableEurekaClient
@SpringBootApplication
public class UserPersistenceMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserPersistenceMicroservicesApplication.class, args);
	}

}

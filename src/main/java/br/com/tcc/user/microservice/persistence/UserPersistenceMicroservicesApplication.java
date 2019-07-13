package br.com.tcc.user.microservice.persistence;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableRabbit
@EnableAsync
@EnableEurekaClient
@SpringBootApplication
public class UserPersistenceMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserPersistenceMicroservicesApplication.class, args);
	}

}

package br.com.tcc.user.microservice.persistence.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfiguration {
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}

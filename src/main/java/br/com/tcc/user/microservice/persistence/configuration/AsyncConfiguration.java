package br.com.tcc.user.microservice.persistence.configuration;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfiguration {
	
	/* taskExecutor is the default method name that spring will searches
	 * if, for any reason it changes, the methods annoted with @Async must change to @Async("new-method-name-here")
	 */
	@Bean
	public Executor taskExecutor() {
	       ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	       executor.setCorePoolSize(200);
	       executor.setMaxPoolSize(200);
	       executor.setThreadNamePrefix("AsynchThreadMethod-");
	       executor.initialize();
	       return executor;
	}

}

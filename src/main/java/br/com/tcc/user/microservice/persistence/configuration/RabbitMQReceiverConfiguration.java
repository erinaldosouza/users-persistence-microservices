package br.com.tcc.user.microservice.persistence.configuration;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQReceiverConfiguration {

	@RabbitListener(queues = {"user-document-sinc-queue"})
	public void receiveMessage(Object message) {
        System.out.println("TESTE <" + message + ">");
    }
}


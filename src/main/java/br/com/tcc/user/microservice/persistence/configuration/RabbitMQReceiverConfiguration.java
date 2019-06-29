package br.com.tcc.user.microservice.persistence.configuration;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import br.com.tcc.user.microservice.persistence.model.impl.User;
import br.com.tcc.user.microservice.persistence.service.UserPersistenceService;
import br.com.tcc.user.microservice.persistence.wrapper.DocumentWrapper;

@Configuration
public class RabbitMQReceiverConfiguration {
	
	private UserPersistenceService userPersistenceService;
	
	public RabbitMQReceiverConfiguration(UserPersistenceService userPersistenceService) {
		this.userPersistenceService = userPersistenceService;
	}

	@RabbitListener(queues = {"user-document-sinc-queue"})
	public void receiveMessage(DocumentWrapper message) {
		User user = userPersistenceService.findById(message.getUserId());
		user.setDocumentId(message.getDocumentId());
		userPersistenceService.update(user);
		
        System.out.println("TESTE <" + message + ">");
    }
}


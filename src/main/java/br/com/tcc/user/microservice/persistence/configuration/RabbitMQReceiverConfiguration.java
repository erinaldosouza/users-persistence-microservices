package br.com.tcc.user.microservice.persistence.configuration;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import br.com.tcc.user.microservice.persistence.model.impl.User;
import br.com.tcc.user.microservice.persistence.repository.UserPersistenceRepository;
import br.com.tcc.user.microservice.persistence.wrapper.DocumentWrapper;

@Configuration
public class RabbitMQReceiverConfiguration {
	
	private UserPersistenceRepository repository;
	
	public RabbitMQReceiverConfiguration(UserPersistenceRepository repository) {
		this.repository = repository;
	}

	@RabbitListener(queues = {"user-document-sinc-queue"})
	public void receiveMessage(DocumentWrapper message) {
		
		switch (message.getOperationCod()) {
			case 1: this.savedOrUpdatedDocument(message);break;
			case 2: this.savedOrUpdatedDocument(message);break;
			case 3: this.documentDeleted(message);break;
		}

    }
	
	private void savedOrUpdatedDocument(DocumentWrapper message) {
		User user = repository.findById(message.getUserId()).get();
		user.setDocumentId(message.getDocumentId());
		repository.save(user);	
		System.out.println("Document saved or updated succefully: " + message);

	}
	
	private void documentDeleted(DocumentWrapper message) {
		System.out.println("Document deleted: " + message);
	}

}


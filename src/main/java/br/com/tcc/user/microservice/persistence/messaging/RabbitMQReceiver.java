package br.com.tcc.user.microservice.persistence.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.tcc.user.microservice.persistence.repository.UserPersistenceRepository;
import br.com.tcc.user.microservice.persistence.wrapper.DocumentWrapper;

@Component
public class RabbitMQReceiver {
	
	private UserPersistenceRepository repository;
	
	public RabbitMQReceiver(UserPersistenceRepository repository) {
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
	
	private void savedOrUpdatedDocument(DocumentWrapper documentWrapper) {
		repository.updateDocumentId(documentWrapper.getDocumentId(), documentWrapper.getUserId());
	}
	
	private void documentDeleted(DocumentWrapper message) {
		System.out.println("Document deleted: " + message);
	}

}


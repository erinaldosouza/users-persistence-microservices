package br.com.tcc.user.microservice.persistence.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.tcc.user.microservice.persistence.wrapper.DocumentWrapper;

@Component
public class RabbitMQPublisher {
	
	private final RabbitTemplate rabbitTemplate;
	
	@Autowired
    public RabbitMQPublisher (RabbitTemplate rabbitTemplate) {
    	this.rabbitTemplate = rabbitTemplate;
    }

	@Async("taskExecutor")
	public void sendAsyncMessage(String topicExchangeName, String  routingkey, DocumentWrapper documentWrapper) {
		rabbitTemplate.convertAndSend(topicExchangeName, routingkey, documentWrapper);
	}

}

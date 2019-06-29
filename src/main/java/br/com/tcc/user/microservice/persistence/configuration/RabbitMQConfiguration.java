package br.com.tcc.user.microservice.persistence.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
	
		@Value("${app.user.persist.document.topic.exchange}")	
	 	private String topicExchangeName;
		
		@Value("${app.user.persist.document.sinc.routingkey}")
		private String routingkey;

	    static String queueName = "user-document-sinc-queue";

	    @Bean
	    Queue queue() {
	        return new Queue(queueName, true);
	    }

	    @Bean
	    TopicExchange exchange() {
	        return new TopicExchange(topicExchangeName);
	    }

	    @Bean
	    Binding binding(Queue queue, TopicExchange exchange) {
	        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	    }
	    
	    @Bean
	    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
	        return new Jackson2JsonMessageConverter();
	    }
	    
	    
//	    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
//	        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//	        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
//	        return rabbitTemplate;
//	    }

//	    @Bean
//	    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
//	        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//	        container.setConnectionFactory(connectionFactory);
//	        container.setQueueNames(queueName);
//	        container.setMessageListener(listenerAdapter);
//	        return container;
//	    }
//
//	    @Bean
//	    MessageListenerAdapter listenerAdapter(RabbitMQReceiverConfiguration receiver) {
//	        return new MessageListenerAdapter(receiver, "receiveMessage");
//	    }

}

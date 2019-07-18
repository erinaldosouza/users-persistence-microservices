package br.com.tcc.user.microservice.persistence.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.tcc.user.microservice.persistence.messaging.RabbitMQPublisher;
import br.com.tcc.user.microservice.persistence.model.impl.User;
import br.com.tcc.user.microservice.persistence.repository.UserPersistenceRepository;
import br.com.tcc.user.microservice.persistence.service.UserPersistenceService;
import br.com.tcc.user.microservice.persistence.wrapper.DocumentWrapper;

@Service
public class UserPersistenceServiceImpl implements UserPersistenceService {
	
	@Value("${document.persistence.service}")
	private String documentPersistenceService;
	
	@Value("${application.services.apikey.name}")
	private String apikeyName;
	
	@Value("${app.user.persist.document.operation.routingkey}")
	private String userDocumentOperationRoutingkey;

	@Value("${app.user.persist.document.topic.exchange}")	
 	private String topicExchangeName;
		
	private final UserPersistenceRepository repository;
	private final RabbitMQPublisher rabbitMQPublisher;
	
	@Autowired
	public UserPersistenceServiceImpl(UserPersistenceRepository repository, RabbitMQPublisher rabbitMQPublisher) {
		this.repository = repository;
		this.rabbitMQPublisher = rabbitMQPublisher;
	}

	@Override
	public User save(User user) {
		MultipartFile document = user.getDocument();
		user = this.repository.save(user);
		user.setDocument(document);
		
		if(document != null) {
			try {
				rabbitMQPublisher.sendAsyncMessage(topicExchangeName, userDocumentOperationRoutingkey, new DocumentWrapper(user, 1));

			} catch (AmqpException | IOException e) {
				e.printStackTrace();
			}
		}
	
		return user;
	}


	@Override
	public User findById(Long id) {
		User user = this.repository.findById(id).orElse(null);
		return user;
	}

	@Override
	public boolean existsById(Long id) {
		return this.repository.existsById(id);
	}

	@Override
	public Iterable<User> findAll() {
		return this.repository.findAll();
	}

	@Override
	public long count() {
		return this.repository.count();
	}

	@Override
	public void deleteById(Long id) {
		Optional<User> opt = repository.findById(id);
		if(opt.isPresent()) {
			User user = opt.get();
			this.repository.delete(user);
			
			if(StringUtils.isNotBlank(user.getDocumentId())) {
				rabbitMQPublisher.sendAsyncMessage(topicExchangeName, userDocumentOperationRoutingkey, new DocumentWrapper(user.getDocumentId(), 3));
			}
		}
	}


	@Override
	public User update(User user) {
		
		Optional<User> opt = repository.findById(user.getId());
		User userBD = null;
		
		if((userBD = opt.orElse(null)) != null) {
			userBD.setLogin(user.getLogin());
			userBD.setPassword(user.getPassword());
				
			repository.save(userBD);
		
			if(user.getDocument() != null) {
				user.setDocumentId(userBD.getDocumentId());
				try {
					rabbitMQPublisher.sendAsyncMessage(topicExchangeName, userDocumentOperationRoutingkey, new DocumentWrapper(user, 2));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}			

		return user;
	}

}

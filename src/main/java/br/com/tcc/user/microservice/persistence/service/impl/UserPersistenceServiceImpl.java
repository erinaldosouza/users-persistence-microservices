package br.com.tcc.user.microservice.persistence.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	private final RabbitTemplate rabbitTemplate;
	
	@Autowired
	public UserPersistenceServiceImpl(UserPersistenceRepository repository, RabbitTemplate rabbitTemplate) {
		this.repository = repository;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public User save(User user) {
		MultipartFile document = user.getDocument();
		user = this.repository.save(user);
		user.setDocument(document);
		
		if(document != null) {
			try {
				rabbitTemplate.convertAndSend(topicExchangeName, userDocumentOperationRoutingkey, new DocumentWrapper(user, 1));
			} catch (AmqpException | IOException e) {
				// TODO Auto-generated catch block
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
				rabbitTemplate.convertAndSend(topicExchangeName, userDocumentOperationRoutingkey, new DocumentWrapper(user.getDocumentId(), 3));

			}
		}
	}


	@Override
	public User update(User user) {
		
		repository.save(user);
		
		if(user.getDocument() != null) {
			try {
				rabbitTemplate.convertAndSend(topicExchangeName, userDocumentOperationRoutingkey, toJsonValue(new DocumentWrapper(user, 2)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}			
			
		
		return user;
	}
	
	private String toJsonValue(Object obj) {
		String json = null;
		
		try {
			json = new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
}

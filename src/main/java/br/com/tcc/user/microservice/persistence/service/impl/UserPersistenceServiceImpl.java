package br.com.tcc.user.microservice.persistence.service.impl;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.tcc.user.microservice.persistence.model.impl.User;
import br.com.tcc.user.microservice.persistence.repository.UserPersistenceRepository;
import br.com.tcc.user.microservice.persistence.service.UserPersistenceService;

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
			//rabbitTemplate.convertAndSend(this.queue.getName(), new DocumentWrapper(user, 1));
		}
	
		return user;
	}

	@Override
	public User findById(Long id) {
		User user = this.repository.findById(id).orElse(null);
		rabbitTemplate.convertAndSend(topicExchangeName, userDocumentOperationRoutingkey, "TESTE NOVOOOOO" /*new DocumentWrapper(user, 1)*/);
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
			
			if(StringUtils.isNotBlank(user.getIdDocument())) {
				//rabbitTemplate.convertAndSend(this.queue.getName(), new DocumentWrapper(user.getIdDocument()));				
			}
		}
	}

	@Override
	public void delete(User user) {
		this.repository.delete(user);
		if(user.getIdDocument() != null) {
		//	rabbitTemplate.convertAndSend(this.queue.getName(), new DocumentWrapper(user.getIdDocument()));			
		}
	}

	@Override
	public User update(User user) {
		User userBD = null;
		Optional<User> optUser = repository.findById(user.getId());
		
		if(optUser.isPresent()) {
			userBD = optUser.get();
			userBD.setLogin(user.getLogin());
			userBD.setPassword(user.getPassword());
			repository.save(userBD);
			
			if(user.getDocument() != null) {
				userBD.setDocument(user.getDocument());
				//rabbitTemplate.convertAndSend(this.queue.getName(),  new DocumentWrapper(userBD, 2));			
			}			
			
		}
		
		return userBD;
	}
}

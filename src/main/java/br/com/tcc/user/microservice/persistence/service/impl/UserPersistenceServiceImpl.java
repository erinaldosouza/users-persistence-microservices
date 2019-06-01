package br.com.tcc.user.microservice.persistence.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import br.com.tcc.user.microservice.persistence.helper.IRequestHelper;
import br.com.tcc.user.microservice.persistence.model.impl.User;
import br.com.tcc.user.microservice.persistence.repository.UserPersistenceRepository;
import br.com.tcc.user.microservice.persistence.service.UserPersistenceService;
import br.com.tcc.user.microservice.persistence.wrapper.DocumentWrapper;

@Service
public class UserPersistenceServiceImpl implements UserPersistenceService {
	
	@Value("${document.persistence.service}")
	private String documentPersistenceService;

	private final UserPersistenceRepository repository;
	private final EurekaClient eurekaClient;
	private final IRequestHelper<DocumentWrapper, User> requestHelper;
	
	@Autowired
	public UserPersistenceServiceImpl(UserPersistenceRepository repository, EurekaClient eurekaClient, IRequestHelper<DocumentWrapper, User> requestHelper) {
		this.repository = repository;
		this.eurekaClient = eurekaClient;
		this.requestHelper = requestHelper;
	}

	@Override
	public <S extends User> S save(S entity) {
		
		if(entity.getPhoto() != null) {
			this.updatePhoto(entity);
		}
	
		return this.repository.save(entity);
	}

	@Override
	public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
		return this.repository.saveAll(entities);
	}

	@Override
	public Optional<User> findById(Long id) {
		return this.repository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return this.repository.existsById(id);
	}

	@Override
	public Iterable<User> findAll() {
		Iterable<User> users = this.repository.findAll();
		/*users.forEach(u -> {
			
		});*/
		return users;
	}

	@Override
	public Iterable<User> findAllById(Iterable<Long> ids) {
		Iterable<User> users = this.repository.findAllById(ids);
		/*users.forEach(u -> {
			
		});*/
		return users;
	}

	@Override
	public long count() {
		return this.repository.count();
	}

	@Override
	public void deleteById(Long id) {
		this.repository.deleteById(id);
	}

	@Override
	public void delete(User entity) {
		this.repository.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends User> entities) {
		this.repository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		this.repository.deleteAll();
	}

	@Override
	public User update(User user) {
		User userBD = null;
		Optional<User> optUser = repository.findById(user.getId());
		
		if(optUser.isPresent()) {
			userBD = optUser.get();
			userBD.setLogin(user.getLogin());
			userBD.setPassword(user.getPassword());
			userBD.setPhoto(user.getPhoto());
			
			if(userBD.getPhoto() != null) {
				this.updatePhoto(userBD);
			}
			
			repository.save(userBD);
		}
		
		return userBD;
	}
	
	
	/**
	 * Persiste the bytes in the binary persistence service, sets the stored file id in the user object and clean the multipart resource
	 * in order to avoid errors when returning the object to service business..
	 * @param user
	 */
	private void updatePhoto(User user) {
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(this.documentPersistenceService, Boolean.FALSE);
		DocumentWrapper documentWrapper = requestHelper.doPost(instanceInfo.getHomePageUrl(), user).getBody();
		user.setIdPhoto(documentWrapper.getDocument().getId());
		user.setPhoto(null);
	}
}

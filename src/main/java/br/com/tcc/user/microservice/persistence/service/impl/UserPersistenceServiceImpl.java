package br.com.tcc.user.microservice.persistence.service.impl;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
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
	private final IRequestHelper<DocumentWrapper> requestHelper;
	
	@Autowired
	public UserPersistenceServiceImpl(UserPersistenceRepository repository, EurekaClient eurekaClient, IRequestHelper<DocumentWrapper> requestHelper) {
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
		
		entities.forEach( e -> {
			updatePhoto(e);
		});
		
		return this.repository.saveAll(entities);
	}

	@Override
	public Optional<User> findById(Long id) {
		Optional<User> opt = this.repository.findById(id);
		if(opt.isPresent()) {
			getPhoto(opt.get());
		}
		return opt;
	}

	@Override
	public boolean existsById(Long id) {
		return this.repository.existsById(id);
	}

	@Override
	public Iterable<User> findAll() {
		Iterable<User> users = this.repository.findAll();
		users.forEach(e -> {
			getPhoto(e);
		});
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
	 * Persists the bytes in the binary persistence service, sets the stored file id in the user object and clean the multipart resource
	 * in order to avoid errors when returning the object to service business..
	 * @param user
	 */
	private void updatePhoto(User user) {
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(this.documentPersistenceService, Boolean.FALSE);
		
		Map<String, Object> map = new HashMap<>();
		map.put("file", user.getPhoto().getResource());
		
		Map<String, String> headersMap = getDefaultHeaders(instanceInfo);	

		DocumentWrapper documentWrapper = requestHelper.doPost(instanceInfo.getHomePageUrl(), map, headersMap).getBody();
		
		user.setIdPhoto(documentWrapper.getDocument().getId());
		user.setPhoto(null);
	}
	
	/**
	 * Persiste the bytes in the binary persistence service, sets the stored file id in the user object and clean the multipart resource
	 * in order to avoid errors when returning the object to service business..
	 * @param user
	 */
	private void getPhoto(User user) {
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(this.documentPersistenceService, Boolean.FALSE);
		
		Map<String, String> headersMap = getDefaultHeaders(instanceInfo);	
		headersMap.put("api-key", instanceInfo.getMetadata().get("api-key"));
		byte[] bytes = requestHelper.doGetBinary(instanceInfo.getHomePageUrl()+ "/img/" + user.getIdPhoto(), headersMap).getBody();
		
		bytes = Base64.getEncoder().encode(bytes);
		user.setBase64Photo(new String(bytes));
	}
	
	private Map<String, String> getDefaultHeaders(InstanceInfo instanceInfo) {
		Map<String, String> headersMap = new HashMap<>();		
		headersMap.put("api-key", instanceInfo.getMetadata().get("api-key"));
		return headersMap;
	}
}

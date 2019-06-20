package br.com.tcc.user.microservice.persistence.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	
	@Value("${application.services.apikey.name}")
	private String apikeyName;
	

	private final UserPersistenceRepository repository;
	private final EurekaClient eurekaClient;
	private final IRequestHelper<DocumentWrapper, MultipartFile> requestHelper;
	
	@Autowired
	public UserPersistenceServiceImpl(UserPersistenceRepository repository, EurekaClient eurekaClient, IRequestHelper<DocumentWrapper, MultipartFile> requestHelper) {
		this.repository = repository;
		this.eurekaClient = eurekaClient;
		this.requestHelper = requestHelper;
	}

	@Override
	public <S extends User> S save(S entity) {
		
		if(entity.getDocument() != null) {
			this.saveDocument(entity);
		}
	
		return this.repository.save(entity);
	}

	@Override
	public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
		
		entities.forEach( e -> {
			saveDocument(e);
		});
		
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
		return this.repository.findAll();
	}

	@Override
	public Iterable<User> findAllById(Iterable<Long> ids) {
		return this.repository.findAllById(ids);
	}

	@Override
	public long count() {
		return this.repository.count();
	}

	@Override
	public void deleteById(Long id) {
//		Optional<User> opt = repository.findById(id);
//		if(opt.isPresent()) {
//			deletePhoto(opt.get());
//		}
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
			userBD.setDocument(user.getDocument());
			
			if(userBD.getDocument() != null) {
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
	private void saveDocument(User user) {
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(this.documentPersistenceService, Boolean.FALSE);
			DocumentWrapper documentWrapper = requestHelper.doPost(instanceInfo.getHomePageUrl(), user.getDocument(), instanceInfo.getMetadata().get(apikeyName)).getBody();
		
		user.setIdDocument(documentWrapper.getDocument().getId());
		user.setDocument(null);
	}
	
	/**
	 * Retrieves the user photo from binary persistence service
	 * @param user
	 */
//	private void getPhoto(User user) {
//		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(this.documentPersistenceService, Boolean.FALSE);
//		byte[] bytes = requestHelper.doGetBinary(instanceInfo.getHomePageUrl()+ "/img/" + user.getIdDocument(), instanceInfo.getMetadata().get(apikeyName)).getBody();
//		
//		bytes = Base64.getEncoder().encode(bytes);
//		user.setBase64Photo(new String(bytes));
//	}
	
	/**
	 * Updates the bytes in the binary persistence service, sets the stored file id in the user object and clean the multipart resource
	 * in order to avoid errors when returning the object to service business..
	 * @param user
	 */
	private void updatePhoto(User user) {
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(this.documentPersistenceService, Boolean.FALSE);
		DocumentWrapper documentWrapper = requestHelper.doPut(instanceInfo.getHomePageUrl() + user.getIdDocument(), user.getDocument(), instanceInfo.getMetadata().get(apikeyName)).getBody();
		
		user.setIdDocument(documentWrapper.getDocument().getId());
		user.setDocument(null);
	}
	
	/**
	 * Deletes the user photo from binary persistence service
	 * @param user
	 */
//	private void deletePhoto(User user) {
//		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(this.documentPersistenceService, Boolean.FALSE);
//		requestHelper.doDelete(instanceInfo.getHomePageUrl() + user.getIdDocument(), instanceInfo.getMetadata().get(apikeyName)).getBody();
//
//	}
}

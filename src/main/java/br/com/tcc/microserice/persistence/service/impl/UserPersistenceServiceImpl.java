package br.com.tcc.microserice.persistence.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tcc.microserice.persistence.model.impl.User;
import br.com.tcc.microserice.persistence.repository.UserPersistenceRepository;
import br.com.tcc.microserice.persistence.service.UserPersistenceService;

@Service
public class UserPersistenceServiceImpl implements UserPersistenceService {
	
	private final UserPersistenceRepository repository;
	
	@Autowired
	public UserPersistenceServiceImpl(UserPersistenceRepository repository) {
		this.repository = repository;
	}

	@Override
	public <S extends User> S save(S entity) {
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
			user.setPassword(user.getPassword());
			repository.save(userBD);
		}
		
		return userBD;
	}

}

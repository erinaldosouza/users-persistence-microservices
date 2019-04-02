package br.com.tcc.user.microservice.persistence.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import br.com.tcc.user.microservice.persistence.model.impl.User;

@NoRepositoryBean
public interface UserPersistenceService extends CrudRepository<User, Long> {
	
	User update(User user);

}

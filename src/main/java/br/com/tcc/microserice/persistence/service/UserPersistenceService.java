package br.com.tcc.microserice.persistence.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import br.com.tcc.microserice.persistence.model.impl.User;

@NoRepositoryBean
public interface UserPersistenceService extends CrudRepository<User, Long> {
	
	User update(User user);

}

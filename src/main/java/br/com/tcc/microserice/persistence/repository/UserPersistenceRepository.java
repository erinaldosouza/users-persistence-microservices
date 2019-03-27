package br.com.tcc.microserice.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.tcc.microserice.persistence.model.impl.User;

@Repository
public interface UserPersistenceRepository extends CrudRepository<User, Long> {

}

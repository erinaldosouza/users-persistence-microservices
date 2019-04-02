package br.com.tcc.user.microservice.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.tcc.user.microservice.persistence.model.impl.User;

@Repository
public interface UserPersistenceRepository extends CrudRepository<User, Long> {

}

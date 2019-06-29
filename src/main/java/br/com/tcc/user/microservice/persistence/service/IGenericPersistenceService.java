package br.com.tcc.user.microservice.persistence.service;

import br.com.tcc.user.microservice.persistence.model.impl.User;

public interface IGenericPersistenceService <T, L>{
	User save(T entity);
	User update(T user);
	User findById(L id);
	Iterable<T> findAll();
	
	boolean existsById(L id);
	long count();
	void deleteById(L id);
}

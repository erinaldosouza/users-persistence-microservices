package br.com.tcc.user.microservice.persistence.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tcc.user.microservice.persistence.model.impl.User;

@Repository
public interface UserPersistenceRepository extends CrudRepository<User, Long> {
	
	@Modifying
	@Transactional
	@Query(name="updateDocumentId")
	public void updateDocumentId(@Param("documentId") String documentId, @Param("id") Long id);

}

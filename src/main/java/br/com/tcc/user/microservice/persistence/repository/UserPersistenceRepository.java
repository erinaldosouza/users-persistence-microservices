package br.com.tcc.user.microservice.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tcc.user.microservice.persistence.model.impl.User;

@Repository
public interface UserPersistenceRepository extends CrudRepository<User, Long> {
	
	@Query(name="updateDocumentId")
	public void updateDocumentId(@Param("documentId") String documentId, @Param("id") Long id);

}

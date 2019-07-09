package br.com.tcc.user.microservice.persistence.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.tcc.user.microservice.persistence.model.impl.User;
import br.com.tcc.user.microservice.persistence.service.UserPersistenceService;
import br.com.tcc.user.microservice.persistence.wrapper.UserWrapper;

@RestController
public class UserPersistenceController {

	private final UserPersistenceService service;	
	
	@Value("${spring.application.name}")
	private String appName;
	
	@Autowired
	public UserPersistenceController(UserPersistenceService service) {
		this.service = service;
	}
	
	@PostMapping(value="/")
	public ResponseEntity<UserWrapper> save(@RequestPart(name="document", required=false) MultipartFile document, @Valid User user) {
		 user = this.service.save(user);	 
		 return ResponseEntity.status(HttpStatus.CREATED).build();				
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<UserWrapper> find(@PathVariable(name="id", required=true) Long id) {
		ResponseEntity<UserWrapper> response = null;
		User user = this.service.findById(id);
		
		if(user != null) {
			response = ResponseEntity.ok(new UserWrapper(user));
		} else {
			response =  ResponseEntity.noContent().build();
		}
		
		return response;
    }
	
	@GetMapping(value="/")
	public ResponseEntity<UserWrapper> findAll() {
		ResponseEntity<UserWrapper> response = null;
		Iterable<User> users = this.service.findAll();
		
		if(users.iterator().hasNext()) {
			response = ResponseEntity.ok(new UserWrapper(users));
		} else {
			response =  ResponseEntity.noContent().build();
		}
		
		return response;
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<UserWrapper> update(@PathVariable(name="id", required=true) Long id, @RequestPart(name="document", required=false) MultipartFile document, @Valid User user) {
		user.setId(id);
		user.setDocument(document);
		this.service.update(user);
		return ResponseEntity.ok().build();
	} 
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<UserWrapper> delete(@PathVariable(name="id", required=true) Long id) {
		this.service.deleteById(id);
		return ResponseEntity.ok().build();
	}
}

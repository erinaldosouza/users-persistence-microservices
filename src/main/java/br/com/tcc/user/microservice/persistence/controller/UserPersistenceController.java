package br.com.tcc.user.microservice.persistence.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcc.user.microservice.persistence.model.impl.User;
import br.com.tcc.user.microservice.persistence.service.UserPersistenceService;

@RestController
public class UserPersistenceController {

	private final UserPersistenceService service;	
	
	@Value("${spring.application.name}")
	private String appName;
	
	@Autowired
	public UserPersistenceController(UserPersistenceService service) {
		this.service = service;
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> save(@RequestBody User user) {
		 user = this.service.save(user); 
		 return ResponseEntity.status(HttpStatus.OK).body(user);				
	}
	
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> find(@PathVariable(value="id", required=true) Long id) {
		Optional<User> opt = this.service.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(opt.orElseGet(null));
    }
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<User>> findAll() {		
		Iterable<User> users = this.service.findAll();		
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	
	@PutMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> update(@PathVariable(value="id", required=true) Long id, @RequestBody(required=true) User user) {
		user = this.service.update(user);
		return ResponseEntity.status(HttpStatus.OK).body(user);		
	} 
	
	@DeleteMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable(value="id", required=true) Long id) {
		System.out.println("Detele request with id: " + id);
		this.service.deleteById(id);
	}
}

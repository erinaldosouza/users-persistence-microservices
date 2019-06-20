package br.com.tcc.user.microservice.persistence.controller;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<UserWrapper> save(@RequestBody(required=true) @Valid User user) {
		 user = this.service.save(user); 		 
		 return ResponseEntity.status(HttpStatus.OK).body(new UserWrapper(user.getId()));				
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<UserWrapper> find(@PathVariable(name="id", required=true) Long id) {
		Optional<User> opt = this.service.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new UserWrapper(opt.orElse(null)));
    }
	
	@GetMapping(value="/")
	public ResponseEntity<UserWrapper> findAll() {		
		Iterable<User> users = this.service.findAll();		
		return ResponseEntity.status(HttpStatus.OK).body(new UserWrapper(users));
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<UserWrapper> update(@PathVariable(name="id", required=true) Long id, @RequestPart(name="photo", required=false) MultipartFile photo, @Valid User user) {
		user.setId(id);
		user.setPhoto(photo);
		user = this.service.update(user);
		return ResponseEntity.status(HttpStatus.OK).body(new UserWrapper(user.getId()));		
	} 
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<UserWrapper> delete(@PathVariable(name="id", required=true) Long id) {
		this.service.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new UserWrapper(id));
	}
}

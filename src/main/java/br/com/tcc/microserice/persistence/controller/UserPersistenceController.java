package br.com.tcc.microserice.persistence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcc.microserice.persistence.model.impl.User;
import br.com.tcc.microserice.persistence.service.UserPersistenceService;

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
	public void save(@RequestBody User user) {
		this.service.save(user);		
		System.out.println("Post request to /save in persistece service: " + user);
	}
	
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void find(@PathVariable(value="id", required=true) Long id) {
		this.service.findById(id);
    }
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void findAll() {
		this.service.findAll();
		System.out.println("Post request to find all method in persistece service: ");

	}
	
	@PutMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@PathVariable(value="id", required=true) Long id, @RequestBody(required=true) User user) {
		User userBd = this.service.update(user);
		System.out.println("Put request with id: " + id + " and body: " + user);
		
	} 
	
	@DeleteMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable(value="id", required=true) Long id) {
		System.out.println("Detele request with id: " + id);
		this.service.deleteById(id);
	}


}

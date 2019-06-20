package br.com.tcc.user.microservice.persistence.wrapper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tcc.user.microservice.persistence.model.impl.User;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)	
public class UserWrapper implements Serializable {

	private static final long serialVersionUID = -4887772344155987297L;
	
	private User user;
	private Long userId;
	private Iterable<User> users;
	
	private String error;
	private String message;

	public UserWrapper(Long userId) {
		this. userId = userId;
	}
	
	public UserWrapper(User user) {
		this.user = user;
	}
	
	public UserWrapper(String message) {
		this.message = message;
	}
	
	public UserWrapper() {
	}

	public UserWrapper(Iterable<User> users) {
		this.users = users;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Iterable<User> getUsers() {
		return users;
	}
	
	public void setUsers(Iterable<User> users) {
		this.users = users;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String errorType) {
		this.error = errorType;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}

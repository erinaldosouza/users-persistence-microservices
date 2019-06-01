package br.com.tcc.user.microservice.persistence.helper;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface IRequestHelper<T, U> {
	public ResponseEntity<T> doRequestDefault(String url, HttpMethod method, U body);	
	public ResponseEntity<T> doRequestDefault(String url, HttpMethod method);
	public ResponseEntity<T> doGet(String url);	
	public ResponseEntity<T> doPost(String url, U body);
	public ResponseEntity<T>  doPut(String url, U body);		
	public ResponseEntity<T> doDelete(String url);
}

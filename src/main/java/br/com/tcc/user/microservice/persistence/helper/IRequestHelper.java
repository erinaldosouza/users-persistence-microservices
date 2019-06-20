package br.com.tcc.user.microservice.persistence.helper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface IRequestHelper<T, B> {
	public ResponseEntity<T> doGet(String url, String apikey);
	public ResponseEntity<byte[]> doGetBinary(String url, String apikey);

	public ResponseEntity<T> doDelete(String url, String apikey);
	public ResponseEntity<T> doPost(String url, B body, String apikey);
	public ResponseEntity<T> doPut(String url, B body, String apikey);
	public ResponseEntity<T> doRequestDefault(String url, HttpMethod method, B body, String apikey);
	
	public ResponseEntity<T> doRequestDefault(String url, HttpMethod method, B body, HttpHeaders httpHeaders);
	public ResponseEntity<byte[]> doRequestDefaultBinary(String url, HttpMethod method, HttpHeaders httpHeaders);	

}

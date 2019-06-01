package br.com.tcc.user.microservice.persistence.helper;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface IRequestHelper<T> {
	public ResponseEntity<T> doGet(String url);
	public ResponseEntity<byte[]> doGetBinary(String url);

	public ResponseEntity<T> doDelete(String url);
	public ResponseEntity<T> doPost(String url, Map<String, Object> body);
	public ResponseEntity<T> doPut(String url, Map<String, Object> body);
	public ResponseEntity<T> doRequestDefault(String url, HttpMethod method);
	
	public ResponseEntity<T> doGet(String url, Map<String, String> headers);
	public ResponseEntity<byte[]> doGetBinary(String url, Map<String, String> headers);

	public ResponseEntity<T> doDelete(String url, Map<String, String> headers);
	public ResponseEntity<T> doPost(String url, Map<String, Object> body, Map<String, String> headers);
	public ResponseEntity<T> doPut(String url, Map<String, Object> body, Map<String, String> headers);

	public ResponseEntity<T> doRequestDefault(String url, HttpMethod method, Map<String, Object> body, Map<String, String> headers);
	public ResponseEntity<byte[]> doRequestDefaultBinary(String url, HttpMethod method, Map<String, String> headers);	

}

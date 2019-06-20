package br.com.tcc.user.microservice.persistence.helper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import br.com.tcc.user.microservice.persistence.helper.IRequestHelper;
import br.com.tcc.user.microservice.persistence.wrapper.DocumentWrapper;

@Component
public class RequestHelperImpl implements IRequestHelper<DocumentWrapper, MultipartFile> {

	private final RestTemplate restTemplate;
	
	@Autowired
	public RequestHelperImpl (RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	private HttpHeaders getHeaders(MediaType mediaType, String apikey) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(mediaType);
		httpHeaders.add("api-key", apikey);
		return httpHeaders;
	}
		
	@Override
	public ResponseEntity<DocumentWrapper> doRequestDefault(String url, HttpMethod method, MultipartFile body, HttpHeaders httpHeaders) {
		
		MultiValueMap<String, Object> map = null;
		
		if(body != null) {
			map = new LinkedMultiValueMap<>();
			map.add("file", body.getResource());
		}
		
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, httpHeaders);
		ResponseEntity<DocumentWrapper> response = restTemplate
				                    .  exchange(url, method, entity, new ParameterizedTypeReference<DocumentWrapper>(){});

		return response;
	}
	
	@Override
	public ResponseEntity<DocumentWrapper> doRequestDefault(String url, HttpMethod method, MultipartFile body, String apikey) {
		return doRequestDefault(url, method, body,  getHeaders(MediaType.APPLICATION_JSON, apikey));
	}
	
	@Override
	public ResponseEntity<DocumentWrapper> doGet(String url, String apikey) {
		return this.doRequestDefault(url, HttpMethod.GET, null, getHeaders(MediaType.APPLICATION_JSON, apikey));		
	}

	@Override
	public ResponseEntity<DocumentWrapper> doPost(String url,  MultipartFile body, String apikey) {
		return this.doRequestDefault(url, HttpMethod.POST, body, getHeaders(MediaType.MULTIPART_FORM_DATA, apikey));
	}
	
	@Override
	public ResponseEntity<DocumentWrapper> doPut(String url,  MultipartFile body, String apikey) {
		return this.doRequestDefault(url, HttpMethod.PUT, body,  getHeaders(MediaType.MULTIPART_FORM_DATA, apikey));
	}
			
	@Override
	public ResponseEntity<DocumentWrapper> doDelete(String url, String apikey) {
		return this.doRequestDefault(url, HttpMethod.DELETE,  null, getHeaders(MediaType.APPLICATION_JSON, apikey));
	}
	

	@Override
	public ResponseEntity<byte[]> doGetBinary(String url, String apikey) {
		return doRequestDefaultBinary(url, HttpMethod.GET, getHeaders(MediaType.APPLICATION_JSON, apikey));
	}

	@Override
	public ResponseEntity<byte[]> doRequestDefaultBinary(String url, HttpMethod method, HttpHeaders getHeaders) {
		
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(getHeaders);
		ResponseEntity<byte[]> response = restTemplate
				                         .exchange(url, method, entity, byte[].class);
		return response;
	}

}

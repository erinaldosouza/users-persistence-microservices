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

import br.com.tcc.user.microservice.persistence.helper.IRequestHelper;
import br.com.tcc.user.microservice.persistence.model.impl.User;
import br.com.tcc.user.microservice.persistence.wrapper.DocumentWrapper;

@Component
public class RequestHelperImpl implements IRequestHelper<DocumentWrapper, User> {
	
	private final RestTemplate restTemplate;
	
	@Autowired
	public RequestHelperImpl (RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
		
	@Override
	public ResponseEntity<DocumentWrapper> doRequestDefault(String url, HttpMethod method, User body) {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		
		if(body != null) {
			try {
				map.add("file",  body.getPhoto().getResource());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, httpHeaders);
		ResponseEntity<DocumentWrapper> response = restTemplate
				               .exchange(url, method, entity, new ParameterizedTypeReference<DocumentWrapper>(){});
		return response;
	}
	
	@Override
	public ResponseEntity<DocumentWrapper> doRequestDefault(String url, HttpMethod method) {
		return doRequestDefault(url, method, null);
	}
	
	@Override
	public ResponseEntity<DocumentWrapper> doGet(String url) {
		return this.doRequestDefault(url, HttpMethod.GET);		
	}
	
	@Override
	public ResponseEntity<DocumentWrapper> doPost(String url, User body) {
		return this.doRequestDefault(url, HttpMethod.POST, body);
	}
	
	@Override
	public ResponseEntity<DocumentWrapper>  doPut(String url, User body) {
		return this.doRequestDefault(url, HttpMethod.PUT, body);
	}
		
	@Override
	public ResponseEntity<DocumentWrapper> doDelete(String url) {
		return this.doRequestDefault(url, HttpMethod.DELETE);
	}
}

package br.com.tcc.user.microservice.persistence.wrapper;

import java.io.Serializable;

import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tcc.user.microservice.persistence.model.impl.User;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)	
public class DocumentWrapper implements Serializable {
	
	private static final long serialVersionUID = 3871870545539290308L;
	
	private Long userId;
	
	private Resource file;
	
	private String resourceId;
	
	private Integer operationCod;
		
	public DocumentWrapper() {
	}
	
	public DocumentWrapper(User user, Integer operationCod) {
		//this.userId = user.getId();
		//this.file = user.getDocument().getResource();
		this.operationCod = operationCod;
	}
	
	public DocumentWrapper(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getOperationCod() {
		return operationCod;
	}

	
	public void setOperationCod(Integer operationCod) {
		this.operationCod = operationCod;
	}
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Resource getFile() {
		return file;
	}

	public void setFile(Resource file) {
		this.file = file;
	}

}

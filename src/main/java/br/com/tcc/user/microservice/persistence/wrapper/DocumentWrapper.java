package br.com.tcc.user.microservice.persistence.wrapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tcc.user.microservice.persistence.model.impl.User;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)	
public class DocumentWrapper implements Serializable {
	
	private static final long serialVersionUID = 3871870545539290308L;
	
	private Long userId;
		
	private String documentId;
	
	private Integer operationCod;
	
	private String filename;
	
	private String contentType;
		
	private byte[] bytes;
			
	public DocumentWrapper() {
	}

	
	public DocumentWrapper(User user, Integer operationCod) throws IOException {
		this.userId = user.getId();
		this.bytes = Base64.getEncoder().encode(IOUtils.toByteArray(user.getDocument().getInputStream()));
		this.operationCod = operationCod;
		this.filename = user.getDocument().getOriginalFilename();
		this.contentType = user.getDocument().getContentType();
		this.documentId = user.getDocumentId();
	}
	
	public DocumentWrapper(String documentId, Integer operatioCod) {
		this.documentId = documentId;
		this.operationCod = operatioCod;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
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

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}

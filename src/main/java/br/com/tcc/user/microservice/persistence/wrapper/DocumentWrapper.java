package br.com.tcc.user.microservice.persistence.wrapper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tcc.user.microservice.persistence.to.DocumentTO;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)	
public class DocumentWrapper implements Serializable {
	
	private static final long serialVersionUID = 3871870545539290308L;
	
	@JsonProperty("file")
	private DocumentTO document;
	
	@JsonProperty("files")
	private Iterable<DocumentTO> documents;
	
	private byte[] bytes;
	
	private String error;
	private String message;
	public DocumentTO getDocument() {
		return document;
	}
	public void setDocument(DocumentTO document) {
		this.document = document;
	}
	public Iterable<DocumentTO> getDocuments() {
		return documents;
	}
	public void setDocuments(Iterable<DocumentTO> documents) {
		this.documents = documents;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}

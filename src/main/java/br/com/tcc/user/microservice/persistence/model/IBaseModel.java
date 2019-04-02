package br.com.tcc.user.microservice.persistence.model;

import java.io.Serializable;

public interface IBaseModel< T extends Serializable> extends Serializable {

	T getId();
	void setId(T t);
}

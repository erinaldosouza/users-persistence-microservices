package br.com.tcc.microserice.persistence.model;

import java.io.Serializable;

public interface IBaseModel< T extends Serializable> extends Serializable {

	T getId();
	void setId(T t);
}

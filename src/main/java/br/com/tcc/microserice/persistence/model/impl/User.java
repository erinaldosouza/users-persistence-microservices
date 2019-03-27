package br.com.tcc.microserice.persistence.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.tcc.microserice.persistence.model.IBaseModel;

@Entity
@Table(name="tb_user", schema="master")
public class User implements IBaseModel<Long> {
	

	private static final long serialVersionUID = 4650828981000577447L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="ds_login", nullable=false, unique=true)
	private String login;
	
	@Column(name="ds_password", nullable=false)
	private String password;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

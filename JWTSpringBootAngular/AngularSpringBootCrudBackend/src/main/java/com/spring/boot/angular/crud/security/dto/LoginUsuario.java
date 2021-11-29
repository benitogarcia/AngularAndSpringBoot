package com.spring.boot.angular.crud.security.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class LoginUsuario {

	@NotNull(message = "El campo Nombre de Usuario no debe ser vacio")
	@Column(unique = true)
	private String username;

	@NotNull(message = "El campo Contraseña no debe ser vacio")
	private String userpass;

	public LoginUsuario() {
	}

	public LoginUsuario(String username, String userpass) {
		this.username = username;
		this.userpass = userpass;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpass() {
		return userpass;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}

}

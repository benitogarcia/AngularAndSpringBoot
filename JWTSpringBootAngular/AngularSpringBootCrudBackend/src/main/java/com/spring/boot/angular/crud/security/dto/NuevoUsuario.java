package com.spring.boot.angular.crud.security.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class NuevoUsuario {
	

	@NotNull(message = "El campo Nombre no debe ser vacio")
	private String nombre;

	@NotNull(message = "El campo Nombre de Usuario no debe ser vacio")
	@Column(unique = true)
	private String username;

	@NotNull(message = "El campo Contraseña no debe ser vacio")
	private String userpass;
	
	@Email(message = "El Correo no tiene el formato correcto")
	private String email;

	private Set<String> roles = new HashSet<>();

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}

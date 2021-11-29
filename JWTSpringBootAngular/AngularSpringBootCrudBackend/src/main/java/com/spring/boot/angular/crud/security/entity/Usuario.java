package com.spring.boot.angular.crud.security.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "usuarios")
@Transactional
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "El campo Nombre no debe ser vacio")
	private String nombre;

	@NotNull(message = "El campo Nombre de Usuario no debe ser vacio")
	@Column(unique = true)
	private String username;

	@NotNull(message = "El campo Contraseña no debe ser vacio")
	private String userpass;
	@Email(message = "El Correo no tiene el formato correcto")
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<Rol> roles = new HashSet<>();

	public Usuario() {
	}

	public Usuario(String nombre, String username, String userpass, String email) {
		this.nombre = nombre;
		this.username = username;
		this.userpass = userpass;
		this.email = email;
	}

	public Usuario(String nombre, String username, String userpass, String email, Set<Rol> roles) {
		this.nombre = nombre;
		this.username = username;
		this.userpass = userpass;
		this.email = email;
		this.roles = roles;
	}

	public Usuario(long id, String nombre, String username, String userpass, String email, Set<Rol> roles) {
		this.id = id;
		this.nombre = nombre;
		this.username = username;
		this.userpass = userpass;
		this.email = email;
		this.roles = roles;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", username=" + username + ", userpass=" + userpass
				+ ", email=" + email + ", roles=" + roles + "]";
	}

}

package com.spring.boot.angular.crud.security.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.angular.crud.security.enums.RolNombre;

@Entity
@Table(name = "roles")
@Transactional
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(message = "El campo ROl no debe ser vacio")
	@Enumerated(EnumType.STRING)
	private RolNombre rolNombre;

	public Rol() {
	}

	public Rol(int id, @NotNull(message = "El campo ROl no debe ser vacio") RolNombre rolNombre) {
		super();
		this.id = id;
		this.rolNombre = rolNombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RolNombre getRolNombre() {
		return rolNombre;
	}

	public void setRolNombre(RolNombre rolNombre) {
		this.rolNombre = rolNombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Rol other = (Rol) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Rol [id=" + id + ", rolNombre=" + rolNombre + "]";
	}

}

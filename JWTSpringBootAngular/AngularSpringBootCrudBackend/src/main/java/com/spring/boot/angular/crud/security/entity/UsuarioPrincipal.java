package com.spring.boot.angular.crud.security.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String nombre;
	private String username;
	private String userpass;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UsuarioPrincipal(String nombre, String username, String userpass, String email,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.nombre = nombre;
		this.username = username;
		this.userpass = userpass;
		this.email = email;
		this.authorities = authorities;
	}
	
	public static UsuarioPrincipal build(Usuario u) {
		
		List<GrantedAuthority> authorities = u.getRoles()
				.stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors.toList());
		
		return new UsuarioPrincipal(u.getNombre(), u.getUsername(), u.getUserpass(), u.getEmail(), authorities);
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return userpass;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEmail() {
		return email;
	}

}

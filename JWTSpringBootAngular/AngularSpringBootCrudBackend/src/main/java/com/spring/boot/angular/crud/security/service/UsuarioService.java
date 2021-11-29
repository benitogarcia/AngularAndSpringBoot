package com.spring.boot.angular.crud.security.service;

import java.util.Optional;

import com.spring.boot.angular.crud.security.entity.Usuario;

public interface UsuarioService {

	Usuario save(Usuario usuario);
	
	Usuario findByUsername(String username);
	
	Optional<Usuario> getFindByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}

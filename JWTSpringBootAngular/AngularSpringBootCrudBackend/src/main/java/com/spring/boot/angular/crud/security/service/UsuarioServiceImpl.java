package com.spring.boot.angular.crud.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.angular.crud.security.entity.Usuario;
import com.spring.boot.angular.crud.security.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario findByUsername(String username) {
		return usuarioRepository.findByUsername(username).orElse(null);
	}

	@Override
	public boolean existsByUsername(String username) {
		return usuarioRepository.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public Optional<Usuario> getFindByUsername(String username) {
		return usuarioRepository.findByUsername(username);
	}

}

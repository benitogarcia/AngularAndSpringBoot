package com.spring.boot.angular.crud.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.angular.crud.security.entity.Rol;
import com.spring.boot.angular.crud.security.enums.RolNombre;
import com.spring.boot.angular.crud.security.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService{
	
	@Autowired
	private RolRepository rolRepository;

	@Override
	public Rol findByRolNombre(RolNombre rolNombre) {
		return rolRepository.findByRolNombre(rolNombre).orElse(null);
	}

}

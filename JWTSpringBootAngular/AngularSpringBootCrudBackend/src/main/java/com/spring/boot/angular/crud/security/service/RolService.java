package com.spring.boot.angular.crud.security.service;

import com.spring.boot.angular.crud.security.entity.Rol;
import com.spring.boot.angular.crud.security.enums.RolNombre;

public interface RolService {

	Rol findByRolNombre(RolNombre rolNombre);
}

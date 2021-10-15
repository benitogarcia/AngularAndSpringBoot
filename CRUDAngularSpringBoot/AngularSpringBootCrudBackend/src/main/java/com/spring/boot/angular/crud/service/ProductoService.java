package com.spring.boot.angular.crud.service;

import java.util.List;

import com.spring.boot.angular.crud.entity.Producto;

public interface ProductoService {

	Producto save(Producto producto);

	List<Producto> findAll();

	Producto findById(Long id);

	Producto findByNombre(String nombre);

	boolean existsByNombre(String nombre);
	
	boolean existsByNombreAndIdNot(String nombre, Long id);
}

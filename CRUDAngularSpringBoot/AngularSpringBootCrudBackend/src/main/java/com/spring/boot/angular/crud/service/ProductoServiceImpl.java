package com.spring.boot.angular.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.angular.crud.entity.Producto;
import com.spring.boot.angular.crud.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	private ProductoRepository productoRepository;

	@Transactional(readOnly = false)
	@Override
	public Producto save(Producto producto) {
		return productoRepository.save(producto);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Producto> findAll() {
		return productoRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Producto findById(Long id) {
		return productoRepository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public Producto findByNombre(String nombre) {
		return productoRepository.findByNombre(nombre).orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean existsByNombre(String nombre) {
		return productoRepository.existsByNombre(nombre);
	}

	@Override
	public boolean existsByNombreAndIdNot(String nombre, Long id) {
		return productoRepository.existsByNombreAndIdNot(nombre, id);
	}

}

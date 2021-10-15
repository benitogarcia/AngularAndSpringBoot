package com.spring.boot.angular.crud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.angular.crud.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
	
	Optional<Producto> findByNombre(String nombre);
	
	boolean existsByNombre(String nombre);
	
	boolean existsByNombreAndIdNot(String nombre, Long id);

}

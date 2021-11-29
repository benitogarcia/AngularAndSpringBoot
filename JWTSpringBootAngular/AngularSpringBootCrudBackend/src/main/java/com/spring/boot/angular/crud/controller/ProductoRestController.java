package com.spring.boot.angular.crud.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.angular.crud.dto.MessageDto;
import com.spring.boot.angular.crud.entity.Producto;
import com.spring.boot.angular.crud.service.ProductoService;

@RestController
@RequestMapping(path = "/api/v1/productos/")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoRestController {

	private static final Logger LOG = LoggerFactory.getLogger(ProductoRestController.class);

	@Autowired
	private ProductoService productoService;

	@PreAuthorize(value = "hasRole('ADMIN')")
	@PostMapping(path = "/save")
	public ResponseEntity<?> save(@Valid @RequestBody Producto producto, BindingResult br) {

		if (br.hasErrors()) {
			LOG.info("Error en el formulario");
			return new ResponseEntity<MessageDto>(new MessageDto("Error en el formulario."), HttpStatus.BAD_REQUEST);
		}

		Producto _producto = new Producto();

		/**
		 * Obtener los datos del producto si se va a modificar.
		 */
		if (producto.getId() > 0) {
			_producto = productoService.findById(producto.getId());

			if (_producto == null) {
				LOG.info("No se encontro el producto a modificar");
				return new ResponseEntity<MessageDto>(new MessageDto("No se encontro el producto a modificar."),
						HttpStatus.BAD_REQUEST);
			}
		}

		/**
		 * if: validar si es un nombre de producto se modifica, que no exista. else: el
		 * producto es nuevo, validar si existe ya existe.
		 */
		if (producto.getId() > 0 && !producto.getNombre().equalsIgnoreCase(_producto.getNombre())
				&& productoService.existsByNombreAndIdNot(producto.getNombre(), producto.getId())) {
			LOG.info("El nombre del producto se encuentra registrado.");
			return new ResponseEntity<MessageDto>(new MessageDto("El nombre del producto se encuentra registrado."),
					HttpStatus.BAD_REQUEST);
		} else if (producto.getId() == 0 && productoService.existsByNombre(producto.getNombre())) {
			LOG.info("El nombre del nueve producto se encuentra registrado.");
			return new ResponseEntity<MessageDto>(new MessageDto("El nombre del producto se encuentra registrado."),
					HttpStatus.BAD_REQUEST);
		}

		_producto = productoService.save(producto);

		if (_producto == null) {
			LOG.info("Error en el servidor, no se modifico el producto.");
			return new ResponseEntity<MessageDto>(new MessageDto("Error en el servidor, no se modifico el producto."),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Producto>(_producto, HttpStatus.CREATED);
	}

	@GetMapping(path = "/all")
	public ResponseEntity<?> findAll() {

		List<Producto> productos = productoService.findAll();

		if (productos.isEmpty() || productos.size() == 0) {
			LOG.info("No se encontro ningun producto.");
			return new ResponseEntity<MessageDto>(new MessageDto("No se encontro ningun producto."),
					HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);

	}

	@GetMapping(path = "/search/id/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") Long id) {

		Producto producto = productoService.findById(id);

		if (producto == null) {
			LOG.info("No se encontro el producto.");
			return new ResponseEntity<MessageDto>(new MessageDto("No se encontro el producto."),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Producto>(producto, HttpStatus.OK);

	}
}

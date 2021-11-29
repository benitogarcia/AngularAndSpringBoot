package com.spring.boot.angular.crud.security.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.angular.crud.dto.MessageDto;
import com.spring.boot.angular.crud.security.dto.JwtDto;
import com.spring.boot.angular.crud.security.dto.LoginUsuario;
import com.spring.boot.angular.crud.security.dto.NuevoUsuario;
import com.spring.boot.angular.crud.security.entity.Rol;
import com.spring.boot.angular.crud.security.entity.Usuario;
import com.spring.boot.angular.crud.security.enums.RolNombre;
import com.spring.boot.angular.crud.security.jwt.JwtProvider;
import com.spring.boot.angular.crud.security.service.RolService;
import com.spring.boot.angular.crud.security.service.UsuarioService;

@RestController
@RequestMapping(path = "/auth/")
@CrossOrigin(origins = "*")
public class AuthRestController {

	private static final Logger LOG = LoggerFactory.getLogger(AuthRestController.class);

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RolService rolService;
	@Autowired
	private JwtProvider jwtProvider;

	@PostMapping(path = "/register")
	public ResponseEntity<?> save(@Valid @RequestBody NuevoUsuario user, BindingResult br) {

		if (br.hasErrors()) {
			return new ResponseEntity<MessageDto>(new MessageDto("Error en el formulario."), HttpStatus.BAD_REQUEST);
		}

		if (usuarioService.existsByUsername(user.getUsername())) {
			return new ResponseEntity<MessageDto>(new MessageDto("El nombre de usuario ya existe."),
					HttpStatus.BAD_REQUEST);
		}

		if (usuarioService.existsByEmail(user.getEmail())) {
			return new ResponseEntity<MessageDto>(new MessageDto("El correo ya existe."), HttpStatus.BAD_REQUEST);
		}

		Usuario usuario = new Usuario(user.getNombre(), user.getUsername(), passwordEncoder.encode(user.getUserpass()),
				user.getEmail(), null);

		Set<Rol> roles = new HashSet<Rol>();

		LOG.info("===> " + RolNombre.ROLE_USER.name());
		roles.add(rolService.findByRolNombre(RolNombre.ROLE_USER));
		LOG.info("===> " + user.getRoles().size());
		LOG.info("===> " + user.getRoles().contains("admin"));

		if (user.getRoles().contains("admin")) {
			LOG.info("===> " + RolNombre.ROLE_ADMIN.name());
			roles.add(rolService.findByRolNombre(RolNombre.ROLE_ADMIN));
		}

		usuario.setRoles(roles);

		Usuario _user = usuarioService.save(usuario);

		if (_user == null) {
			return new ResponseEntity<MessageDto>(new MessageDto("Error en el servidor"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<MessageDto>(new MessageDto("Usuario creado"), HttpStatus.CREATED);

	}

	@PostMapping(path = "/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario lu, BindingResult br) {

		if (br.hasErrors()) {
			return new ResponseEntity<MessageDto>(new MessageDto("Error en el formulario."), HttpStatus.BAD_REQUEST);
		}

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(lu.getUsername(), lu.getUserpass()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		UserDetails details = (UserDetails) authentication.getPrincipal();

		JwtDto jwtDto = new JwtDto(token, lu.getUsername(), details.getAuthorities());

		return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.CREATED);

	}

}

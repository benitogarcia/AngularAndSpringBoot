package com.spring.boot.angular.crud.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.spring.boot.angular.crud.security.entity.UsuarioPrincipal;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * gENERAR TOKEN, validar
 * 
 * @author benito
 *
 */
@Component
public class JwtProvider {

	private static final Logger LOG = LoggerFactory.getLogger(JwtProvider.class);

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private int expiration;

	public String generateToken(Authentication authentication) {
		UsuarioPrincipal principal = (UsuarioPrincipal) authentication.getPrincipal();
		return Jwts.builder().setSubject(principal.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public String getNombreUsuarioFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			LOG.error("token mal formado");
		} catch (UnsupportedJwtException e) {
			LOG.error("token no soportado");
		} catch (IllegalArgumentException e) {
			LOG.error("token vacio");
		} catch (SignatureException e) {
			LOG.error("error en la firma");
		}
		return false;
	}
}

package com.javaudemy.SpringBoot_Ionic.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	public String generateToken(String username) {
		
		String secret = System.getenv().get("JWT_SECRET");
		Long expiration = Long.parseLong(System.getenv().get("JWT_EXPIRATION"));
		
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
}

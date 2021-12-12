package org.bz.nisum.ms.app.usuarios.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.bz.nisum.ms.app.usuarios.entities.User;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AuthController implements EnvironmentAware {
	
	private Environment environment;

	@PostMapping("auth")
	public User login(@RequestParam("user") String name, @RequestParam("password") String pwd) {
		
		System.out.println("user: ".concat(name)
				.concat("\npassword: ").concat(pwd));
		
		String token = getJWTToken(name);
		User user = new User();
		user.setName(name);
		user.setToken(token);		
		return user;
		
	}

	private String getJWTToken(String username) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("millisecond.expiration"))))
				.signWith(SignatureAlgorithm.HS512,
						environment.getProperty("secret.key").getBytes()).compact();

		return "Bearer " + token;
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.environment = environment;
	}

}

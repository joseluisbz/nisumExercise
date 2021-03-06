package org.bz.nisum.ms.app.usuarios.controllers;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.bz.nisum.ms.app.usuarios.entities.User;
import org.bz.nisum.ms.app.usuarios.errors.DefaultException;
import org.bz.nisum.ms.app.usuarios.errors.ExistingMailException;
import org.bz.nisum.ms.app.usuarios.errors.PatternEmailException;
import org.bz.nisum.ms.app.usuarios.errors.PatternPasswordException;
import org.bz.nisum.ms.app.usuarios.services.UserServiceIface;
import org.bz.nisum.ms.app.usuarios.validations.UserPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserServiceIface userService;
	
	@Autowired UserPasswordValidator userPasswordValidator;
	
	@Operation(summary = "list Users", security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping
	public ResponseEntity<?> list(){
		return ResponseEntity.ok().body(userService.findAll());
	}

	@Operation(summary = "view User", security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping("/{id}")
	public ResponseEntity<?> view(@PathVariable Long id) {
		
		Optional<User> optionalStoredUser = userService.findById(id);
		if (!optionalStoredUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(optionalStoredUser.get());
	}

	@Operation(summary = "create User", security = @SecurityRequirement(name = "bearerAuth"))
	@PostMapping
	public ResponseEntity<?> create(@RequestHeader(value="Authorization") String token, 
			@Valid @RequestBody User user, BindingResult result) {
		
		userPasswordValidator.validate(user, result);
		throwExceptionIfErrors(result);
		
		Optional<User> optionalStoredUser = userService.findByEmail(user.getEmail());
		if (optionalStoredUser.isPresent()) {
			throw new ExistingMailException(user.getEmail());
		}
		
		user.setIsactive(true);
		user.setToken(token);
		
		User createdUser = userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@Operation(summary = "edit User", security = @SecurityRequirement(name = "bearerAuth"))
	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@RequestHeader(value="Authorization") String token, 
			@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {
		
		userPasswordValidator.validate(user, result);
		throwExceptionIfErrors(result);
		
		if (user.getEmail() != null && !user.getEmail().isEmpty()) {
			boolean emailUsed = userService.findByEmailAndIdNot(user.getEmail(), id).size() > 0;
			if (emailUsed) {
				throw new ExistingMailException(user.getEmail());
			}
		}
		
		Optional<User> optionalStoredUser = userService.findById(id);
		if (!optionalStoredUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		User editedUser = optionalStoredUser.get();
		editedUser.setName(user.getName());
		editedUser.setEmail(user.getEmail());
		editedUser.setPassword(user.getPassword());
		editedUser.setPhones(user.getPhones());
		editedUser.setModified(new Date());
		editedUser.setIsactive(user.isIsactive());
		editedUser.setToken(token);
		try {
			User updatedUser = userService.save(editedUser);
			return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
		} catch (Exception exp) {
			throw new DefaultException(exp.getLocalizedMessage());
		}
	}

	@Operation(summary = "delete User", security = @SecurityRequirement(name = "bearerAuth"))
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		userService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	private void throwExceptionIfErrors(BindingResult result) {
		if (result.hasErrors()) {
			FieldError passwordFieldError = result.getAllErrors()
					.stream().map(e -> (FieldError) e)
					.filter(f -> f.getField().equals("password"))
					.findFirst().orElse(null);
			
			if (passwordFieldError != null) {
				throw new PatternPasswordException(passwordFieldError.getDefaultMessage());
			}
			FieldError emailFieldError = result.getAllErrors()
					.stream().map(e -> (FieldError) e)
					.filter(f -> f.getField().equals("email"))
					.findFirst().orElse(null);
			
			if (emailFieldError != null) {
				throw new PatternEmailException(emailFieldError.getDefaultMessage());
			}
		}
	}
}

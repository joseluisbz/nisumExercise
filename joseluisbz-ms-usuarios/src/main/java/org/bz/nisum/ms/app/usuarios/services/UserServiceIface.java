package org.bz.nisum.ms.app.usuarios.services;

import java.util.List;
import java.util.Optional;

import org.bz.nisum.ms.app.usuarios.entities.User;

public interface UserServiceIface {
	
	public Iterable<User> findAll();
	public Optional<User> findById(Long id);
	public Optional<User> findByEmail(String email);
	public List<User> findByEmailAndIdNot(String email, Long id);
	public User save(User user);
	public void deleteById(Long id);

}

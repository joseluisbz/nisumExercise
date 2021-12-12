package org.bz.nisum.ms.app.usuarios.daos;

import java.util.List;
import java.util.Optional;

import org.bz.nisum.ms.app.usuarios.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "nisum")
public interface UserDao extends CrudRepository<User, Long> {
	Optional<User> findFirstByEmail(String email);
	List<User> findByEmailAndIdNot(String email, Long id);
}
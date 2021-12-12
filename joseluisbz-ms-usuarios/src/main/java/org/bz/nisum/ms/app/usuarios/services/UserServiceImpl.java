package org.bz.nisum.ms.app.usuarios.services;

import java.util.List;
import java.util.Optional;

import org.bz.nisum.ms.app.usuarios.daos.UserDao;
import org.bz.nisum.ms.app.usuarios.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserServiceIface {
	
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional(readOnly = true)
	public Iterable<User> findAll() {
		return userDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(Long id) {
		return userDao.findById(id);
	}

	
	@Override
	public Optional<User> findByEmail(String email) {
		return userDao.findFirstByEmail(email.toLowerCase());
	}

	@Override
	public List<User> findByEmailAndIdNot(String email, Long id) {
		return userDao.findByEmailAndIdNot(email.toLowerCase(), id);
	}

	@Override
	@Transactional
	public User save(User user) {
		user.setEmail(user.getEmail().toLowerCase());
		return userDao.save(user);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		userDao.deleteById(id);
	}

}

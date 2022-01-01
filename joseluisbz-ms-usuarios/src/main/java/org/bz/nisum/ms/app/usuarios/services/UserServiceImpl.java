package org.bz.nisum.ms.app.usuarios.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.bz.nisum.ms.app.usuarios.daos.PhoneDao;
import org.bz.nisum.ms.app.usuarios.daos.UserDao;
import org.bz.nisum.ms.app.usuarios.entities.Phone;
import org.bz.nisum.ms.app.usuarios.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserServiceIface {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PhoneDao phoneDao;

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
		if (user.getId() != null) {
			phoneDao.deleteByUser_Id(user.getId());
			List<Phone> listOldPhones = phoneDao.findByUser_Id(user.getId());
			Set<Phone> listNewPhones = user.getPhones();
			if (listNewPhones != null && listOldPhones != null) {
				Set<Long> listOldIds = listOldPhones.stream().map(p -> p.getId()).collect(Collectors.toSet());
				Set<Long> listNewIds = listNewPhones.stream().map(p -> p.getId()).collect(Collectors.toSet());
				listOldIds.stream().filter(id -> !listNewIds.contains(id)).forEach(id -> phoneDao.deleteById(id));
			}
		}
		user.setEmail(user.getEmail().toLowerCase());
		return userDao.save(user);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		userDao.deleteById(id);
	}

}

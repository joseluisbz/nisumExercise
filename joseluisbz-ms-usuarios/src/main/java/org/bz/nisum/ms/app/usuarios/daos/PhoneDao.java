package org.bz.nisum.ms.app.usuarios.daos;

import java.util.List;

import org.bz.nisum.ms.app.usuarios.entities.Phone;
import org.springframework.data.repository.CrudRepository;

public interface PhoneDao extends CrudRepository<Phone, Long> {
	List<Phone> deleteByUser_Id(Long id);
	List<Phone> findByUser_Id(Long id);
}

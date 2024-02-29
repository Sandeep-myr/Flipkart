package com.rt.pot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rt.pot.model.Admin;
import com.rt.pot.model.Contacts;

@Repository
public interface ContactsRepo extends JpaRepository<Contacts, Integer> {

	public Contacts findByEmailId(String emailId);
	boolean existsByEmailId(String emailId);

	Contacts findAllByEmailId(String emailId);

	List<Contacts> findByAdminData(Admin admin);

}

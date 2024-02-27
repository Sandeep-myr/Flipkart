package com.rt.pot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rt.pot.model.Manager;

@Repository
public interface ManagerRepo extends JpaRepository<Manager, Integer> {

	public Manager findByEmailIdIgnoreCase(String emailId);

}

package com.rt.pot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rt.pot.model.LoginDetails;

@Repository
public interface LoginDetailsRepo extends JpaRepository<LoginDetails, Integer> {

	public Boolean existsByUserEmailId(String userEmailId);

	public LoginDetails findByUserEmailIdIgnoreCase(String emailId);

	public void deleteByUserEmailId(String emailId);

}

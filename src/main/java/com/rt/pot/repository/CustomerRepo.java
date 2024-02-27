package com.rt.pot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rt.pot.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	Customer findByEmailIdIgnoreCase(String emailId);

	Customer findByMobileNumber(Long mobileNumber);

}

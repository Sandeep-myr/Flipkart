package com.rt.pot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rt.pot.model.Address;
import com.rt.pot.model.Admin;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {

	List<Address> findByAdminData(Admin admin);

	Address findByCityAndStreetAndStateAndCountryAndZipCode(String city, String street, String state, String country,
			Integer zipCode);

}

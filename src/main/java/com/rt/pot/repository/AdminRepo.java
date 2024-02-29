
package com.rt.pot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rt.pot.model.Admin;
import com.rt.pot.model.Contacts;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface AdminRepo extends JpaRepository<Admin, Integer> {
	
	public Admin findByAdminContacts(Contacts contacts);

	public List<Admin> findByIsApproved(String isApproved);

	@Modifying
	@Query(value = "UPDATE admin_details SET active=:action WHERE admin_id=:admin_id", nativeQuery = true)
	public Integer updateStatus(@Param("admin_id") Integer admin_id, @Param("action") String action);
}

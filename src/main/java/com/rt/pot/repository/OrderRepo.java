package com.rt.pot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rt.pot.model.Admin;
import com.rt.pot.model.OrderDetails;
import com.rt.pot.model.Product;

@Repository
public interface OrderRepo extends JpaRepository<OrderDetails, Long> {

	public List<OrderDetails> findByAdmin(Admin admin);

	public List<OrderDetails> findByProduct(Product product);

}

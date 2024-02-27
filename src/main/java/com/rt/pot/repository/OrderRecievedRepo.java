package com.rt.pot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rt.pot.model.Customer;
import com.rt.pot.model.OrderRecived;


@Repository
public interface OrderRecievedRepo extends JpaRepository<OrderRecived, Integer> {

	List<OrderRecived> findByCustomer(Customer customer);
	
	@Query(value="select * from order_recieved   where year(order_date) =:year",nativeQuery = true)
	List<OrderRecived> findByYearRange(@Param("year") Integer year );

}

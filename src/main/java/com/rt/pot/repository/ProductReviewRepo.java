package com.rt.pot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rt.pot.model.OrderDetails;
import com.rt.pot.model.ProductReview;

@Repository
public interface ProductReviewRepo extends JpaRepository<ProductReview, Integer> {

	public ProductReview findByOrderDetails(OrderDetails orderDetails);

}

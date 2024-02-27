package com.rt.pot.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rt.pot.error.NotLogin;
import com.rt.pot.model.Customer;
import com.rt.pot.model.OrderDetails;
import com.rt.pot.model.ProductReview;
import com.rt.pot.modelReq.ProductReviewRequest;
import com.rt.pot.repository.OrderRepo;
import com.rt.pot.repository.ProductReviewRepo;
import com.rt.pot.service.IOrderService;

import jakarta.servlet.http.HttpSession;

@Service
public class OrderServiceImpl implements  IOrderService {

	@Autowired
	private ProductReviewRepo productReviewRepo;
	
	
	@Autowired
	private OrderRepo orderRepo;
	// We Are Collect The Customer Review When The Customer Placed The Order And Write Review
	
	@Override
	public String customerReview(ProductReviewRequest productReviewRequest,String emailId) {
	
				
		
		OrderDetails orderDetails = orderRepo.findById(productReviewRequest.getOrderId()).get();
		
		
		
		ProductReview productReview = new ProductReview();
		productReview.setOrderDetails(orderDetails);
		productReview.setComment(productReviewRequest.getComment());
		productReview.setCustomerPublicName(productReviewRequest.getCustomerPublicName());
		productReview.setReviewHeadline(productReviewRequest.getReviewHeadline());
		productReview.setStarRating(productReviewRequest.getStarRating());
		
		productReviewRepo.save(productReview);
		
		
		return "Thanks For Your FeedBack ";
	}

}

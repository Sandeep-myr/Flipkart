package com.rt.pot.service;

import com.rt.pot.modelReq.ProductReviewRequest;

import jakarta.servlet.http.HttpSession;

public interface IOrderService {
	

	String customerReview(ProductReviewRequest productReviewRequest,String emailId);

}

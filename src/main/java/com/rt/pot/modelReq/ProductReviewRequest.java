package com.rt.pot.modelReq;

import lombok.Data;

@Data
public class ProductReviewRequest {

	private Long orderId;
	private Integer starRating;
	private String reviewHeadline;
	private String comment;
	private String customerPublicName;
}

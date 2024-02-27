package com.rt.pot.modelResponse;

import lombok.Data;

@Data
public class ProductReviewResponse {

	private Integer starRating;
	private String reviewHeadline;
	private String comment;
	private String customerPublicName;

}

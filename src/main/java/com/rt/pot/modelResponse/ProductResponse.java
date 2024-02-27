package com.rt.pot.modelResponse;

import java.util.List;

import lombok.Data;

@Data
public class ProductResponse {
	
	
	
	private Integer productId;
	
	private String productName;

	private String brandName;

	private Integer productQty;

	private Double productPrice;

	private Double discountPercentage;

	private String description;

	private String imageUrl;
	
	@Data
	public class ProdutResponseWithRating{
		
		private ProductResponse productResponse;
		
		private Double starRating;
		
		private List<ProductReviewResponse> productReviewResponseList;
	}
	
	

}

package com.rt.pot.modelResponse;

import java.time.LocalDateTime;
import java.util.List;

import com.rt.pot.modelReq.AddressReq;

import lombok.Data;

@Data
public class Order {
	@Data
	public class AddToCartResponse {

		@Data
		public class ProductCart {
			private Integer productId;
			private String productName;
			private String brandName;
			private String description;
			private String imageUrl;
			private Integer productQty;

			private Double productPrice;
			private Double totalProductPurchasePrice;
			private Double discountAmount;
			private Double afterDiscountAmount;
			private String sellerName;
			private Long orderId;
		}

		private List<ProductCart> productCartList;
		private Double totalPayableAmount;
		private Double totalSaving;
		private Double totalBillAmount;

	}

	private LocalDateTime orderDate;
	private AddToCartResponse addToCartResponse;
	private AddressReq addressReq;

}

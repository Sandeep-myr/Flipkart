package com.rt.pot.modelResponse;

import java.time.LocalDateTime;
import java.util.List;

import com.rt.pot.orderEnum.OrderStatus;

import lombok.Data;

@Data
public class SellerOrderRecieved {

	@Data
	public class ProductDetails {
		private String productName;
		private String brandName;
		private String description;
		private String imageUrl;
		private Integer productQty;

		private Double productPrice;
		private Double totalProductPurchasePrice;
		private Double discountAmount;
		private Double afterDiscountAmount;
		private OrderStatus orderStatus;
	}

	private List<ProductDetails> productDetailsList;
	private Integer orderRecievedId;
	private Double totalPayableAmount;
	private Double totalSaving;
	private Double totalBillAmount;
	private LocalDateTime orderDate;
	private AddressResponse addressResponse;

	private CustomerResponse customerResponse;

}

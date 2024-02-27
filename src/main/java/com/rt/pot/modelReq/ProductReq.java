package com.rt.pot.modelReq;

import lombok.Data;

@Data
public class ProductReq {

	private String productName;

	private String brandName;

	private Integer productQty;

	private Double productPrice;

	private Double discountPercentage;

	private String description;

	private String imageUrl;

}

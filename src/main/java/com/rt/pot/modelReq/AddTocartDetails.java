package com.rt.pot.modelReq;

import com.rt.pot.model.Product;

import lombok.Data;

@Data
public class AddTocartDetails {

	private Product product;

	private Integer productQty;

	private Double totalPrice;

	private Double discount;

	private Double payableAmount;

}

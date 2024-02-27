package com.rt.pot.util;

import com.rt.pot.model.OrderDetails;
import com.rt.pot.model.Product;
import com.rt.pot.modelResponse.SellerOrderRecieved;
import com.rt.pot.modelResponse.SellerOrderRecieved.ProductDetails;

public class AdminOrderUtilityManager {

	public static ProductDetails productDetailsSetting(OrderDetails orderSummary, Double totalPayingAmount,
			Double discountPrice, Double totalProductPrice,SellerOrderRecieved sellerOrderRecieved) {

		SellerOrderRecieved.ProductDetails productDetails = sellerOrderRecieved.new ProductDetails();
		Product orderProduct = orderSummary.getProduct();
		productDetails.setProductName(orderProduct.getProductName());
		productDetails.setBrandName(orderProduct.getBrandName());
		productDetails.setDescription(orderProduct.getDescription());
		productDetails.setImageUrl(orderProduct.getImageUrl());
		productDetails.setProductQty(orderSummary.getProductQty());
		productDetails.setProductPrice(orderProduct.getProductPrice());
		productDetails.setOrderStatus(orderSummary.getOrderStatus());
		productDetails.setAfterDiscountAmount(totalPayingAmount);
		productDetails.setDiscountAmount(discountPrice);
		productDetails.setTotalProductPurchasePrice(totalProductPrice);
return productDetails;
	}

}

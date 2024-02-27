package com.rt.pot.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rt.pot.model.Category;
import com.rt.pot.model.Product;
import com.rt.pot.modelResponse.CategoryResponse;
import com.rt.pot.modelResponse.ProductResponse;

@Component

public class EntityToResponseMapping {

	public List<ProductResponse> convertProductEntityToProductResponse(List<Product> productList) {
		List<ProductResponse> productResponseList = new ArrayList<>();

		productList.stream().forEach(product -> {
			ProductResponse productResponse = new ProductResponse();
			productResponse.setProductId(product.getProductId());
			productResponse.setProductName(product.getProductName());
			productResponse.setBrandName(product.getBrandName());
			productResponse.setDescription(product.getDescription());
			productResponse.setDiscountPercentage(product.getDiscountPercentage());
			productResponse.setImageUrl(product.getImageUrl());
			productResponse.setProductPrice(product.getProductPrice());
			productResponse.setProductQty(product.getProductQty());
			productResponseList.add(productResponse);
		});
		return productResponseList;

	}

	public ProductResponse convertProductEntityToProductResposne(Product product) {

		ProductResponse productResponse = new ProductResponse();
		productResponse.setProductId(product.getProductId());
		productResponse.setProductName(product.getProductName());
		productResponse.setBrandName(product.getBrandName());
		productResponse.setDescription(product.getDescription());
		productResponse.setDiscountPercentage(product.getDiscountPercentage());
		productResponse.setImageUrl(product.getImageUrl());
		productResponse.setProductPrice(product.getProductPrice());
		productResponse.setProductQty(product.getProductQty());

		return productResponse;
	}

	public CategoryResponse convertCategoryEntityToCategoryResponse(Category category) {
		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setCategoryid(category.getCategoryId());
		categoryResponse.setCategoryName(category.getCategoryName());
		return categoryResponse;
	}

}

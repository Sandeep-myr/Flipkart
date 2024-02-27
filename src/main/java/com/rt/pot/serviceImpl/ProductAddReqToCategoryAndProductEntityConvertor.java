package com.rt.pot.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.pot.model.Category;
import com.rt.pot.model.Product;
import com.rt.pot.modelReq.CateogaryReq;
import com.rt.pot.modelReq.ProductReq;
import com.rt.pot.repository.CategoryRepo;
import com.rt.pot.repository.ProductsRepo;

@Component
public class ProductAddReqToCategoryAndProductEntityConvertor {

	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ProductsRepo productRepo;
	
	

	public Set<Category> catogoryReqSetToCatogoryEntitySet(Set<Category> categories, // khali hai
			Set<CateogaryReq> categoryReqs) {

		categoryReqs.stream().forEach((CateogaryReq) -> {

			Category category = categoryRepo.findByCategoryName(CateogaryReq.getCategoryName());

			if (category == null) {
				Category category2 = new Category();

				category2.setProductList(ProductReqToProductEntity(CateogaryReq.getProductList(), category2));
				category2.setCategoryName(CateogaryReq.getCategoryName());
				categories.add(category2);

			} else {
				
				category.setProductList(ProductReqToProductEntity(CateogaryReq.getProductList(), category));
				category.setCategoryName(category.getCategoryName());
				categories.add(category);

			}

		});

		return categories;

	}

	private Set<Product> ProductReqToProductEntity(Set<ProductReq> productReqSet, Category category) {
		Set<Product> productSet = new HashSet<>();
		List<Product> productList = productRepo.findByAdminId(CategoryProductServiceImpl.admin.getAdminId());

			
		if(productList.isEmpty()) {
			for (ProductReq productReq : productReqSet) {
				 Product newProduct = new Product();
			        newProduct.setProductName(productReq.getProductName());
			        newProduct.setBrandName(productReq.getBrandName());
			        newProduct.setDescription(productReq.getDescription());
			        newProduct.setDiscountPercentage(productReq.getDiscountPercentage());
			        newProduct.setProductPrice(productReq.getProductPrice());
			        newProduct.setProductQty(productReq.getProductQty());
			        newProduct.setImageUrl(productReq.getImageUrl());
			        newProduct.setAdminData(CategoryProductServiceImpl.admin);
			        newProduct.setCategory(category);
			        productSet.add(newProduct);
			}
			return productSet;
		}
		
		
		for (ProductReq productReq : productReqSet) {
			
			Product product = productRepo.findByProductNameAndBrandNameAndDescriptionAndDiscountPercentageAndProductPriceAndImageUrlAndAdminData(productReq.getProductName(), productReq.getBrandName(), productReq.getDescription(), productReq.getDiscountPercentage(), productReq.getProductPrice(), productReq.getImageUrl(),CategoryProductServiceImpl.admin);
		   
		
		    if (product==null) {
		        Product newProduct = new Product();
		        newProduct.setProductName(productReq.getProductName());
		        newProduct.setBrandName(productReq.getBrandName());
		        newProduct.setDescription(productReq.getDescription());
		        newProduct.setDiscountPercentage(productReq.getDiscountPercentage());
		        newProduct.setProductPrice(productReq.getProductPrice());
		        newProduct.setProductQty(productReq.getProductQty());
		        newProduct.setImageUrl(productReq.getImageUrl());
		        newProduct.setAdminData(CategoryProductServiceImpl.admin);
		        newProduct.setCategory(category);
		        productSet.add(newProduct);
		    
		    }
		    else {
  
		    	
		    	int newQty = product.getProductQty()+productReq.getProductQty();
		    	
		    	product.setProductQty(newQty);
          
            
		    	
		        productSet.add(product);
		   
		    }
		   
		}
		
		return productSet;

	}

}

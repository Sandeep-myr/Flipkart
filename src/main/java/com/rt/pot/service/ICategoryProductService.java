package com.rt.pot.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rt.pot.error.DataGettingException;
import com.rt.pot.error.NoProductFoundWithThisName;
import com.rt.pot.modelReq.DateRange;
import com.rt.pot.modelReq.InventoryRefil;
import com.rt.pot.modelReq.RemoveProduct;
import com.rt.pot.modelReq.addReq;
import com.rt.pot.modelResponse.CategoryResponse;
import com.rt.pot.modelResponse.InventoryResponse;
import com.rt.pot.modelResponse.ProductResponse;
import com.rt.pot.modelResponse.ProductResponse.ProdutResponseWithRating;
import com.rt.pot.modelResponse.TurnOver;

import jakarta.servlet.http.HttpSession;

public interface ICategoryProductService {

	String addTheProduct(addReq addReq,String emailId);

	ResponseEntity<List<ProductResponse>> findAllProduct();

	ResponseEntity<List<ProductResponse>> searchProduct(String searchKey) throws NoProductFoundWithThisName;

	ResponseEntity<List<ProductResponse>> searchProductByCategoryName(String categoryName)
			throws NoProductFoundWithThisName;

	ResponseEntity<List<ProductResponse>> sortProductPriceAsc() throws NoProductFoundWithThisName;

	ResponseEntity<List<ProductResponse>> sortProductPriceDesc() throws NoProductFoundWithThisName;

	ProdutResponseWithRating findByProductId(Integer productId);

	List<ProductResponse> findAccordingToRating(Double ratingRange);

	List<CategoryResponse> findAllCategory();

	List<ProductResponse> findAllProductForSpecificCategory(Integer categoryId) throws DataGettingException;

	List<String> findAllProductBrandAccordingToCategory(Integer categoryId);

	List<ProductResponse> findByBrandName(String brandName, Integer catgoryId) throws DataGettingException;

      Map<ProductResponse,Integer> mostSellingProduct();

	TurnOver sellsGraphYearWise(DateRange dateRange,String emailId);

	List<InventoryResponse> inventoryControl(String emailId);

	String inventoryRefil(  InventoryRefil inventoryRefil);

	String removeProductInInventory(RemoveProduct removeProduct);

}

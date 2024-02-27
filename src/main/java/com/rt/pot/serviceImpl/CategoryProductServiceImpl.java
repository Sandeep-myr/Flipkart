package com.rt.pot.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rt.pot.error.DataGettingException;
import com.rt.pot.error.NoProductFoundWithThisName;
import com.rt.pot.error.NotLogin;
import com.rt.pot.model.Admin;
import com.rt.pot.model.Category;
import com.rt.pot.model.Manager;
import com.rt.pot.model.OrderDetails;
import com.rt.pot.model.OrderRecived;
import com.rt.pot.model.Product;
import com.rt.pot.model.ProductReview;
import com.rt.pot.modelReq.DateRange;
import com.rt.pot.modelReq.InventoryRefil;
import com.rt.pot.modelReq.RemoveProduct;
import com.rt.pot.modelReq.addReq;
import com.rt.pot.modelResponse.AdminResponse;
import com.rt.pot.modelResponse.CategoryResponse;
import com.rt.pot.modelResponse.EntityToDtoMapping;
import com.rt.pot.modelResponse.InventoryResponse;
import com.rt.pot.modelResponse.ProductResponse;
import com.rt.pot.modelResponse.ProductResponse.ProdutResponseWithRating;
import com.rt.pot.modelResponse.ProductReviewResponse;
import com.rt.pot.modelResponse.TurnOver;
import com.rt.pot.repository.AdminRepo;
import com.rt.pot.repository.CategoryRepo;
import com.rt.pot.repository.ContactsRepo;
import com.rt.pot.repository.ManagerRepo;
import com.rt.pot.repository.OrderRecievedRepo;
import com.rt.pot.repository.OrderRepo;
import com.rt.pot.repository.ProductReviewRepo;
import com.rt.pot.repository.ProductsRepo;
import com.rt.pot.service.ICategoryProductService;

import jakarta.servlet.http.HttpSession;

@Service
public class CategoryProductServiceImpl implements ICategoryProductService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ProductAddReqToCategoryAndProductEntityConvertor addReqToCategoryAndProductEntityConvertor;

	@Autowired
	private ContactsRepo contactsRepo;
	@Autowired
	private ManagerRepo managerRepo;
	@Autowired
	
	private ProductsRepo productsRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private ProductReviewRepo productReviewRepo;

	@Autowired
	private OrderRecievedRepo orderRecievedRepo;

	@Autowired
	private EntityToResponseMapping entityToResponseMapping;

	@Autowired
	private AdminRepo adminRepo;
	public static Admin admin;

	@Override
	public String addTheProduct(addReq addReq, String emailId) {// CategoryReq
					Set<Category> categorySet = new HashSet<>();

			categoryRepo.saveAll(this.addReqToCategoryAndProductEntityConvertor
					.catogoryReqSetToCatogoryEntitySet(categorySet, addReq.getCategoryList()));
		
		return "products are added";
	}

	// =================================================================================

	// FIND ALL THE PRODUCT ARE AVAILABLE IN THE INVENTORY

	@Override
	public ResponseEntity<List<ProductResponse>> findAllProduct() {

		List<Product> productList = productsRepo.findAll();
		List<ProductResponse> productResponses = entityToResponseMapping
				.convertProductEntityToProductResponse(productList);

		return new ResponseEntity<>(productResponses, HttpStatus.OK);
	}

	// =================================================================================
	// SEARCH THE PRODUCT WITH SOME KEY WORD

	@Override
	public ResponseEntity<List<ProductResponse>> searchProduct(String searchKey) throws NoProductFoundWithThisName {

		List<Product> productList = productsRepo.findByKeyword(searchKey);

		if (productList.isEmpty()) {
			throw new NoProductFoundWithThisName("No Product Found With This Name");
		}
		List<ProductResponse> productResponses = entityToResponseMapping
				.convertProductEntityToProductResponse(productList);

		return new ResponseEntity<>(productResponses, HttpStatus.OK);

	}

	// =================================================================================
	// SEARCH THE PRODUCT WITH CATEGORY NAME

	@Override
	public ResponseEntity<List<ProductResponse>> searchProductByCategoryName(String categoryName)
			throws NoProductFoundWithThisName {

		List<Product> productList = productsRepo.findByCategoryName(categoryName);

		if (productList.isEmpty()) {
			throw new NoProductFoundWithThisName("No Product Found With This Name");
		}
		List<ProductResponse> productResponses = entityToResponseMapping
				.convertProductEntityToProductResponse(productList);

		return new ResponseEntity<>(productResponses, HttpStatus.OK);

	}

	// =================================================================================
	// SEARCH THE PRODUCT PRICE WISE LOW TO HIGH
	@Override
	public ResponseEntity<List<ProductResponse>> sortProductPriceAsc() throws NoProductFoundWithThisName {

		List<Product> productList = productsRepo.findAllByOrderByProductPriceAsc();

		if (productList.isEmpty()) {
			throw new NoProductFoundWithThisName("No Product Found With This Name");
		}
		List<ProductResponse> productResponses = entityToResponseMapping
				.convertProductEntityToProductResponse(productList);

		return new ResponseEntity<>(productResponses, HttpStatus.OK);

	}

	// =================================================================================
	// SEARCH THE PRODUCT PRICE WISE HIGH TO LOW

	@Override
	public ResponseEntity<List<ProductResponse>> sortProductPriceDesc() throws NoProductFoundWithThisName {

		List<Product> productList = productsRepo.findAllByOrderByProductPriceDesc();

		if (productList.isEmpty()) {
			throw new NoProductFoundWithThisName("No Product Found With This Name");
		}
		List<ProductResponse> productResponses = entityToResponseMapping
				.convertProductEntityToProductResponse(productList);

		return new ResponseEntity<>(productResponses, HttpStatus.OK);

	}

	// =================================================================================

	// FIND THE PRODUCT BY ID

	@Override
	public ProdutResponseWithRating findByProductId(Integer productId) {

		ProductResponse response = new ProductResponse();
		ProductResponse.ProdutResponseWithRating productResponseWithRating = null;
		Product product = productsRepo.findById(productId).get();

		// converting product to product response;

		ProductResponse productResponse = entityToResponseMapping.convertProductEntityToProductResposne(product);

		List<OrderDetails> orderDetailsList = orderRepo.findByProduct(product);

		List<ProductReview> productReviewList = new ArrayList<>();

		for (OrderDetails orderDetails : orderDetailsList) {

			ProductReview productReview = productReviewRepo.findByOrderDetails(orderDetails);
			if (productReview != null) {
				productReviewList.add(productReview);
			}
		}

		List<ProductReviewResponse> productReviewResponseList = new ArrayList<>();

		Double averageStarRating = 0.0;
		Double totalRating = 0.0;
		for (ProductReview productReview : productReviewList) {

			ProductReviewResponse productReviewResponse = new ProductReviewResponse();
			productReviewResponse.setComment(productReview.getComment());
			productReviewResponse.setCustomerPublicName(productReview.getCustomerPublicName());
			productReviewResponse.setReviewHeadline(productReview.getReviewHeadline());
			productReviewResponse.setStarRating(productReview.getStarRating());
			totalRating += productReview.getStarRating();

			productReviewResponseList.add(productReviewResponse);
		}
		averageStarRating = totalRating / productReviewList.size();

		productResponseWithRating = response.new ProdutResponseWithRating();
		productResponseWithRating.setProductResponse(productResponse);
		productResponseWithRating.setProductReviewResponseList(productReviewResponseList);
		productResponseWithRating.setStarRating(averageStarRating);

		return productResponseWithRating;
	}

	// ===========================================================================================

	// FINDING THE PRODUCT ACCORDING TO RATING

	@Override
	public List<ProductResponse> findAccordingToRating(Double ratingRange) {
		List<Product> productList = productsRepo.findAll();
		List<ProductResponse> productResponseList = new ArrayList<>();
		ProductResponse response = new ProductResponse();
		productList.stream().forEach(product -> {
			ProductResponse.ProdutResponseWithRating productResponseWithRating = response.new ProdutResponseWithRating();
			productResponseWithRating = this.findByProductId(product.getProductId());
			if (productResponseWithRating.getStarRating() >= ratingRange) {
				ProductResponse productResponse = entityToResponseMapping
						.convertProductEntityToProductResposne(product);
				productResponseList.add(productResponse);
			}
		});
		return productResponseList;
	}

	// ===========================================================================================

	// FINDING THE ALL CATEGORY

	@Override
	public List<CategoryResponse> findAllCategory() {
		List<Category> categoryList = categoryRepo.findAll();

		List<CategoryResponse> categoryResponseList = new ArrayList<>();
		// Convert the Category To Category Response
		categoryList.stream().forEach(category -> {
			CategoryResponse categoryResponse = new CategoryResponse();
			categoryResponse = entityToResponseMapping.convertCategoryEntityToCategoryResponse(category);
			categoryResponseList.add(categoryResponse);
		});
		return categoryResponseList;
	}

	// ===========================================================================================

	// FIND THE PRODUCT FOR SEPECIFIC CATEGORY USING CATEGORY ID

	@Override
	public List<ProductResponse> findAllProductForSpecificCategory(Integer categoryId) throws DataGettingException {
		List<ProductResponse> productResponseList = null;
		try {
			Category category = categoryRepo.findById(categoryId).get();
			List<Product> productList = productsRepo.findByCategory(category);

			// conversion PRODUCT ENTITY LIS TO PRODUCT RESPONSE LIST

			productResponseList = new ArrayList<>();

			productResponseList = entityToResponseMapping.convertProductEntityToProductResponse(productList);

		} catch (Exception e) {
			throw new DataGettingException(e.getMessage());
		}
		return productResponseList;
	}

	// ===========================================================================================

	// BRAND LISTY IN SPECIFIC CATEGORY

	@Override
	public List<String> findAllProductBrandAccordingToCategory(Integer categoryId) {

		List<String> brandList = productsRepo.findBrandAccordingToCategoryId(categoryId);

		return brandList;
	}

	// ===========================================================================================

	// PRODUCT LIST IN SPECIFIC BRAND IN SPECIFIC CATEGORY

	@Override
	public List<ProductResponse> findByBrandName(String brandName, Integer catgoryId) throws DataGettingException {
		List<ProductResponse> productResponseList = null;
		try {
			List<Product> productList = productsRepo.findProductByBrandInSpecificCategory(brandName.toUpperCase(),
					catgoryId);

			// conversion PRODUCT ENTITY LIS TO PRODUCT RESPONSE LIST

			productResponseList = new ArrayList<>();

			productResponseList = entityToResponseMapping.convertProductEntityToProductResponse(productList);
		} catch (Exception e) {
			throw new DataGettingException(e.getMessage());
		}
		return productResponseList;
	}

	// =========================================================================================

	// MOST SELLING PRODUCT

	@Override
	public Map<ProductResponse, Integer> mostSellingProduct() {
		Map<ProductResponse, Integer> sellMap = new HashMap<>();

		List<OrderDetails> orderDetailslist = orderRepo.findAll();

		Set<Integer> products = new TreeSet<>();

		for (OrderDetails orderDetails : orderDetailslist) {

			products.add(orderDetails.getProduct().getProductId());

		}

		for (Integer productid : products) {
			Integer sellQty = 0;
			ProductResponse productResponse = null;
			for (OrderDetails orderDetails : orderDetailslist) {

				if (orderDetails.getProduct().getProductId() == productid) {
					sellQty += orderDetails.getProductQty();

				}
				productResponse = entityToResponseMapping
						.convertProductEntityToProductResposne(productsRepo.findById(productid).get());

			}

			sellMap.put(productResponse, sellQty);
		}

		return sellMap;
	}

	// ===============================================================================================

	// Year Wise Sells Record

	@Override
	public TurnOver sellsGraphYearWise(DateRange dateRange, String emailId) {
		
		TurnOver turnOver = new TurnOver();
		
			Map<Integer, Double> yearWiseSell = new HashMap<>();

			Double overAllRevenue = 0.0;
			for (Integer i = dateRange.getStartingDate(); i <= dateRange.getEndingTime(); i++) {

				Double yearlyRevenue = 0.0;
				List<OrderRecived> orderRecivedList = orderRecievedRepo.findByYearRange(i);

				for (OrderRecived orderRecived : orderRecivedList) {
					yearlyRevenue += orderRecived.getAmountPay();
				}
				overAllRevenue += yearlyRevenue;
				yearWiseSell.put(i, yearlyRevenue);
			}
			turnOver.setYearWiseSellsMap(yearWiseSell);
			turnOver.setTotalSells(overAllRevenue);

		return turnOver;
	}

	// =============================================================================

	// CHECK THE INVENTORY BY MANAGER

	@Override
	public List<InventoryResponse> inventoryControl( String emailId) {
		List<InventoryResponse> inventoryResponses = new ArrayList<>();
			List<Product> productList = productsRepo.findAll();
			Set<Integer> sellers = new HashSet<>();
			for (Product product : productList) {
				sellers.add(product.getAdminData().getAdminId());
			}

			for (Integer sellerId : sellers) {
				List<Product> productsPerSeller = new ArrayList<>();
				for (Product product : productList) {
					if (product.getAdminData().getAdminId() == sellerId) {
						productsPerSeller.add(product);
					}
					InventoryResponse inventoryResponse = new InventoryResponse();
					List<ProductResponse> productResponses = entityToResponseMapping
							.convertProductEntityToProductResponse(productsPerSeller);
					inventoryResponse.setProductResponses(productResponses);
					AdminResponse adminResponse = EntityToDtoMapping
							.adminToAdminResponse(adminRepo.findById(sellerId).get());
					inventoryResponse.setAdminResponse(adminResponse);
					inventoryResponses.add(inventoryResponse);
				}
			}

		
		return inventoryResponses;
	}

	// ===============================================================================================================================

// INVENTORY REFIL BY SELLER 	
	@Override
	public String inventoryRefil( InventoryRefil inventoryRefil) {
		Admin admin = contactsRepo.findAllByEmailId(inventoryRefil.getSellerEmailId()).getAdminData();
		
		String message = null;
		

			Product product = productsRepo.findById(inventoryRefil.getProductId()).get();
			Integer updated = productsRepo.refillProduct(admin.getAdminId(),
					product.getProductQty() + inventoryRefil.getNewProductQty(), inventoryRefil.getProductId());

			if (updated > 0) {
				message = "Product Requirnment Fulfilled";
			} else {
				message = "Product Requirnment Not Fulfilled";
			}
				return message;

	}

	// ===============================================================================================================================

	// Product Deleted By Manager
	
	@Override
	public String removeProductInInventory( RemoveProduct removeProduct) {
		Manager manager =  managerRepo.findByEmailIdIgnoreCase(removeProduct.getEmailId().toUpperCase());
		if(manager!=null){
			if(removeProduct.getProductId()!=null) {
			productsRepo.deleteById(removeProduct.getProductId());
			}
		}
		else {
		throw new 	NotLogin("Fistly Login!!!");
		}
		return "Product Removed Successfully!!!";
	}
	
	
	
	
	
}

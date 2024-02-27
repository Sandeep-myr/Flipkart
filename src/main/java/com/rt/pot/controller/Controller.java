package com.rt.pot.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rt.pot.error.DataGettingException;
import com.rt.pot.error.EmailIdDoesNotExist;
import com.rt.pot.error.InvalidCredentials;
import com.rt.pot.error.NoProductFoundWithThisName;
import com.rt.pot.error.PotException;
import com.rt.pot.error.SavingException;
import com.rt.pot.model.Address;
import com.rt.pot.model.Admin;
import com.rt.pot.model.Contacts;
import com.rt.pot.model.Customer;
import com.rt.pot.model.Manager;
import com.rt.pot.modelReq.AddressReq;
import com.rt.pot.modelReq.AdminReq;
import com.rt.pot.modelReq.CustomerReq;
import com.rt.pot.modelReq.DateRange;
import com.rt.pot.modelReq.InventoryRefil;
import com.rt.pot.modelReq.ManagerReq;
import com.rt.pot.modelReq.OrderRequest;
import com.rt.pot.modelReq.ProductReviewRequest;
import com.rt.pot.modelReq.RemoveProduct;
import com.rt.pot.modelReq.addReq;
import com.rt.pot.modelResponse.AdminResponse;
import com.rt.pot.modelResponse.CategoryResponse;
import com.rt.pot.modelResponse.CustomerResponse;
import com.rt.pot.modelResponse.InventoryResponse;
import com.rt.pot.modelResponse.ManagerResponse;
import com.rt.pot.modelResponse.Order;
import com.rt.pot.modelResponse.ProductResponse;
import com.rt.pot.modelResponse.ProductResponse.ProdutResponseWithRating;
import com.rt.pot.modelResponse.SellerOrderRecieved;
import com.rt.pot.modelResponse.TurnOver;
import com.rt.pot.service.IAdminService;
import com.rt.pot.service.ICategoryProductService;
import com.rt.pot.service.ICustomerService;
import com.rt.pot.service.IManagerService;
import com.rt.pot.service.IOrderService;
import com.rt.pot.serviceImpl.MapperOfEntity;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("flipkart")
@CrossOrigin(origins = {"http://localhost:4200","https://5516-183-82-102-74.ngrok-free.app"})
public class Controller {

	@Autowired
	private IAdminService adminService;

	@Autowired
	private IManagerService managerService;

	@Autowired
	private ICategoryProductService categoryProductService;

	@Autowired
	private ICustomerService customerService;

	@Autowired
	private IOrderService orderService;

	// ==============================================================================================

	// 1. AdminRegistraion Work Do By This Api

	@PostMapping("adminReg")

	public ResponseEntity<String> registerAdmin(@Valid @RequestBody AdminReq adminReq)
			throws MessagingException, PotException {
		try {
			// set Personal Details

			Admin admin = new Admin(); // 1
			admin.setAdminName(adminReq.getAdminName());

			// setContactDetails

			Set<Contacts> adminContacts = new HashSet<>();
			adminReq.getAdminContacts().forEach(ContactsReq -> {
				Contacts contacts = new Contacts();//
				contacts.setAdminData(admin);
				adminContacts.add(MapperOfEntity.contactReqToContact(ContactsReq, contacts));

			});

			admin.setAdminContacts(adminContacts);

			// setAddressDetails

			Set<Address> adminAddresses = new HashSet<>();
			adminReq.getAdminAddresses().forEach(AddressReq -> {
				Address address = new Address();
				address.setAdminData(admin);

				adminAddresses.add(MapperOfEntity.AddressReqToAddress(AddressReq, address));

			});
			admin.setAdminAddresses(adminAddresses);

			return ResponseEntity.ok(this.adminService.registerAdmin(admin));
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==============================================================================================

	// 2. MANAGER REGISTRATION DO BY THIS API

	@PostMapping("/managerReg")
	public ResponseEntity<String> registerManager(@RequestBody ManagerReq managerReq) throws PotException {
		try {
			Manager manager = new Manager();
			manager.setManagerName(managerReq.getManagerName());

			manager.setMobileNumber(managerReq.getMobileNumber());
			manager.setEmailId(managerReq.getEmailId());
			manager.setStreet(managerReq.getStreet());
			manager.setCity(managerReq.getCity());
			manager.setState(managerReq.getState());
			manager.setCountry(managerReq.getCountry());
			manager.setZipCode(managerReq.getZipCode());
			manager.setUserName(managerReq.getManagerName());
			manager.setPassword(managerReq.getPassword());

			return ResponseEntity.ok(this.managerService.registerManager(manager));
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==============================================================================================

	// 3. FETCHING ALL ADMIN DETAILS BY THIS API

	@GetMapping("/allAdminDetails")
	public ResponseEntity<List<AdminResponse>> fetchAdminRequest() throws PotException {
		try {
			return ResponseEntity.ok(this.managerService.findAllAdminRequest());
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==============================================================================================

	// 4. FETCHING ALL THE PENDING REQUEST WHO'S DOES NOT APPROVED BY THE MANAGER

	@GetMapping("/pendingRequest")
	public ResponseEntity<List<AdminResponse>> pendingRequest() throws PotException {
		try {
			return this.managerService.pendingRequest();
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==============================================================================================

	// 5. API FOR APPROVAL BY THE MANAGER

	@PutMapping("/approveAdmin")
	public ResponseEntity<String> approveRequest(@RequestParam("emailId") String emailId,
			@RequestParam("action") boolean action) throws MessagingException, PotException {
		try {
			return this.managerService.approveRequest(emailId, action);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==============================================================================================

	// 6. API FOR ADMIN LOGIN

	@GetMapping("adminLogin")
	public ResponseEntity<AdminResponse> adminLoginDetails(@RequestParam("emailId") String emailId,
			@RequestParam("password") String password)
			throws EmailIdDoesNotExist, InvalidCredentials, PotException {
		try {
			return this.adminService.login(emailId, password );
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==============================================================================================

	// 7. API FOR THE ADMIN LOGOUT

	@GetMapping("/logout")
	public ResponseEntity<String> logout(@RequestParam("emailId") String emailId, HttpSession session)
			throws PotException {
		// Retrieve the logged-in admin from the session
		try {
			Admin loggedInAdmin = (Admin) session.getAttribute(emailId.toUpperCase());

			if (loggedInAdmin != null) {
				// Remove the attribute for the logged-in admin
				session.removeAttribute("admin");
				return ResponseEntity.ok("Logged out successfully for " + loggedInAdmin.getAdminName());
			} else {
				// Handle the case where no admin is found in the session
				return ResponseEntity.ok("No admin logged in");
			}

		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==============================================================================================

	// 8. ADDING THE PRODUCT BY ADMIN WHO'S LOGIN

	@PostMapping("/addProduct")
	public String addProduct(@RequestBody addReq addReq, @RequestParam("emailId") String emailId)
			throws PotException {
		try {
			return this.categoryProductService.addTheProduct(addReq,  emailId);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// =============================================================================================

	// 9. FIND THE ALL PRODUCT LIST WITH DETAILS

	@GetMapping("/all/product")
	public ResponseEntity<List<ProductResponse>> getAllProduct() throws PotException {
		try {
			return this.categoryProductService.findAllProduct();

		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==============================================================================================

	// 10. Searching The Product By Name Or Category Or Description

	@GetMapping("/searchProduct")
	public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam("key") String searchKey)
			throws NoProductFoundWithThisName, PotException {
		try {
			return this.categoryProductService.searchProduct(searchKey);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==============================================================================================

	// 11. Searching The Product BY CATEGORY NAME

	@GetMapping("/searchByCategoryName")
	public ResponseEntity<List<ProductResponse>> searchProductByCategoryName(
			@RequestParam("category") String categoryName) throws NoProductFoundWithThisName, PotException {
		try {
			return this.categoryProductService.searchProductByCategoryName(categoryName);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==============================================================================================

	// 12. SORT THE ELEMENT BY PRICE LOW TO HIGH

	@GetMapping("/sortPriceAsc")
	public ResponseEntity<List<ProductResponse>> sortTheProductPriceAsc()
			throws NoProductFoundWithThisName, PotException {
		try {
			return this.categoryProductService.sortProductPriceAsc();
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==============================================================================================

	// 13. SORT THE ELEMENT BY PRICE HIGH TO LOW

	@GetMapping("/sortPriceDesc")
	public ResponseEntity<List<ProductResponse>> sortTheProductPricedESC()
			throws NoProductFoundWithThisName, PotException {
		try {
			return this.categoryProductService.sortProductPriceDesc();
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==============================================================================================

	// 14. DELETE THE ADMIN BY THE MANAGER AND SEND THE MAIL

	@DeleteMapping("/deleteAdmin")
	public ResponseEntity<String> deleteTheAdminRecordByManager(@RequestParam("emailId") String emailId,
 @RequestParam("managerEmailId") String managerEmailId)
			throws MessagingException, PotException {
		try {
			return this.managerService.deletAdminRecordByManager(emailId, managerEmailId);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ===============================================================================================

	// 15. UPDATE THE ADMIN DETAILS BY IT SELF LIKE NAME PASSWORD CONTACT DETAILS
	// AND ADDRESS

	@PutMapping("/updateAdmin")
	public ResponseEntity<String> updateAdminDetails(@RequestParam("emailId") String emailId,
			@RequestBody AdminReq adminReq) throws PotException {
		try {
			return this.adminService.updateAdminDetails(emailId, adminReq);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==================================================================================================

	// 16. Customer Registration

	@PostMapping("/customerRegistration")
	public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerReq customerReq)
			throws SavingException, PotException {
		try {
			return this.customerService.customerRegistration(customerReq);

		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// =================================================================================================

	// 17. Customer Profile Details

	@GetMapping("/customerDetails")
	public ResponseEntity<CustomerResponse> getCustomerDetails(
			@RequestParam(name = "mobileNumber", required = false) Long mobileNumber,
			@RequestParam(name = "emailId", required = false) String emailId)
			throws DataGettingException, PotException {
		try {
			return this.customerService.getCustomerDetails(mobileNumber, emailId);

		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==================================================================================================

	// 18. Customer password change api When The Customer Is Login

	@PostMapping("/password/change")
	public ResponseEntity<String> customerPasswordChange(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("emailId") String emailId, @RequestParam("newPassword") String newPassword
			) throws DataGettingException, PotException {
		try {
			System.out.println("Login ");
			return this.customerService.changePassword(oldPassword, emailId, newPassword);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==================================================================================================

	// 19. //CUSTOMER LOGIN API AND STORE THE CUSTOMER RECORD IN THE SESSION UNTIL
	// THE LOGOUT

	@PostMapping("/customerLogin")
	public ResponseEntity<CustomerResponse> customerLoginVerification(
			@RequestParam(name = "emailId", required = false) String emailId,
			@RequestParam(name = "mobileNumber", required = false) Long mobileNumber,
			@RequestParam(name = "password", required = true) String password)
			throws DataGettingException, PotException {
		try {
			return this.customerService.customerLoginVerifiaction(emailId, mobileNumber, password);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==================================================================================================

	// 20. CUSTOMER LOGOUT SYSTEM AND RELEASE THE SESSION

//	@PostMapping("/customerLogout")
//	public ResponseEntity<String> customerLogoutFacility(
//			@RequestParam(name = "emailId", required = true) String emailId)
//			throws DataGettingException, PotException {
//		try {
//			Customer customer = (Customer) httpsession.getAttribute(emailId.toUpperCase());
//			if (customer != null) {
//				httpsession.removeAttribute(emailId);
//			} else {
//				
//				
//				
//				
//				
//				throw new DataGettingException("No Customer Login Exception");
//			}
//
//			return new ResponseEntity<>("Customer Logout Successfully", HttpStatus.OK);
//		} catch (Exception e) {
//			throw new PotException(e.getMessage());
//		}
//	}

	// ===================================================================================================

	// 21.ORDER PLACING BY THE CUSTOMER

	@PostMapping("/addToCart")
	public ResponseEntity<String> productAddToCart(@RequestParam("productId") Integer productId,
			@RequestParam("emailId") String emailId, @Valid @RequestBody OrderRequest orderReq)
			throws DataGettingException, PotException {
		try {
			return this.customerService.addToCart(emailId, orderReq, productId);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ===================================================================================================

	// 22. VIEW CART TO SEE WHAT I AM ADDING THE PRODUCT AND HOW MUCH QTY AND PRICE

	@GetMapping("/viewcart")
	public ResponseEntity<Order.AddToCartResponse> viewCart(@RequestParam("emailId") String emailId
			) throws DataGettingException, PotException {
		try {
			return this.customerService.viewCart(emailId );
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ====================================================================================================

	// 23. CHECKOUT THE CART AND PAY THE BILL

	@PutMapping("/checkOut")
	public ResponseEntity<String> checkOut(@RequestParam("emailId") String emailId,
			@RequestBody AddressReq addressReq) throws DataGettingException, PotException {
		try {

			return this.customerService.checkOutCart(emailId, addressReq);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ====================================================================================================

	// 25. SEE THE ORDER HISTORY

	@GetMapping("/orderHistory")
	public ResponseEntity<List<Order>> orderHistory(@RequestParam("emailId") String emailId)
			throws PotException {
		try {
			return new ResponseEntity<>(this.customerService.orderHistory(emailId), HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ====================================================================================================

	// 26.Seller Order List

	@GetMapping("/sellerOrderRecieved")
	public ResponseEntity<List<SellerOrderRecieved>> sellerOrderRecieved(@RequestParam("emailId") String emailId
			) throws DataGettingException, PotException {
		try {
			return new ResponseEntity<>(this.adminService.getOrderDetails(emailId ), HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ====================================================================================================

	// 27. Seller Order Status Update

	@PutMapping("order/status/{statusCode}")
	public ResponseEntity<String> orderStatusUpdate(@PathVariable("statusCode") Integer statusCode,
			@RequestParam("emailId") String emailId,
			@RequestParam("orderRecievedId") Integer orderRecivedId) throws PotException {
		try {
			return new ResponseEntity<>(
					this.adminService.orderStatusUpdate(emailId, statusCode, orderRecivedId),
					HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}

	}

	// ====================================================================================================

	// 28. Review Of Product

	@PostMapping("/order/review")
	public ResponseEntity<String> reviewOfProduct(@RequestBody ProductReviewRequest productReviewRequest, @RequestParam("emailId") String emailId) throws PotException {
		try {
			return new ResponseEntity<>(this.orderService.customerReview(productReviewRequest, emailId),
					HttpStatus.OK);

		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ====================================================================================================
	// 29. Find The product By ProductId

	@GetMapping("/productById")
	public ResponseEntity<ProdutResponseWithRating> findProductById(@RequestParam("productId") Integer productId)
			throws PotException {
		try {
			return new ResponseEntity<>(this.categoryProductService.findByProductId(productId), HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ====================================================================================================

	// 30 SORT THE PRODUCT ACCORDING TO RATING

	@GetMapping("/product/ratingSort")
	public ResponseEntity<List<ProductResponse>> findProductinRatingRange(
			@RequestParam("ratingRange") Double ratingRange) throws PotException {
		try {
			return new ResponseEntity<>(this.categoryProductService.findAccordingToRating(ratingRange), HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ====================================================================================================

	// 31. Find All Category Available in Website

	@GetMapping("/product/allCategory")
	public ResponseEntity<List<CategoryResponse>> findAllCategoryProduct() throws PotException {
		try {
			return new ResponseEntity<>(this.categoryProductService.findAllCategory(), HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ====================================================================================================

	// 32. FIND ALL PRODUCT BY CATEGORY ID

	@GetMapping("/product/productCategoryWise")
	public ResponseEntity<List<ProductResponse>> findAllProductForSpecificCategory(
			@RequestParam("categoryId") Integer categoryId) throws DataGettingException, PotException {
		try {
			return new ResponseEntity<>(this.categoryProductService.findAllProductForSpecificCategory(categoryId),
					HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ====================================================================================================

	// 33. FIND ALL BRAND AVAILABLE IN CATEGORY

	@GetMapping("/AllBrandInSpecificCategory")
	public ResponseEntity<List<String>> findAllProductBrandAccordingToCategory(
			@RequestParam("categoryId") Integer categoryId) throws PotException {
		try {
			return new ResponseEntity<>(this.categoryProductService.findAllProductBrandAccordingToCategory(categoryId),
					HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ====================================================================================================

	// 34. FIND ALL PRODUCT FOR SPECIFIC BRAND

	@GetMapping("/allProductForSpecificBrand")
	public ResponseEntity<List<ProductResponse>> allProductForSpecificBrand(@RequestParam("brandName") String brandName,
			@RequestParam("catgoryId") Integer catgoryId) throws DataGettingException, PotException {
		try {
			return new ResponseEntity<>(this.categoryProductService.findByBrandName(brandName, catgoryId),
					HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ====================================================================================================

	// 35. Manager Login System

	@PostMapping("/managerLoginDetails")
	public ResponseEntity<ManagerResponse> managerLogin(@RequestBody ManagerReq managerReq)
			throws InvalidCredentials, PotException {
		try {
			return new ResponseEntity<>(this.managerService.managerLogin(managerReq ), HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ====================================================================================================

	// 36. MOST SELLING PRODUCT

	@GetMapping("/productSellingGraph")
	public ResponseEntity<Map<ProductResponse, Integer>> mostSelling() throws PotException {
		try {
			return new ResponseEntity<>(this.categoryProductService.mostSellingProduct(), HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ====================================================================================================

	// 37. YEAR WISE SELL RECORD TOTAL TURNOVER

	@GetMapping("/sells")
	public ResponseEntity<TurnOver> totalTurnOver(@RequestBody DateRange dateRange,
			String emailId) throws PotException {

		try {

			return new ResponseEntity<>(this.categoryProductService.sellsGraphYearWise(dateRange, emailId),
					HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}

	}

	// ====================================================================================================

	// 38. Manager Watch Inventory

	@GetMapping("/inventory")

	public ResponseEntity<List<InventoryResponse>> inventoryControl(String emailId)
			throws PotException {
		try {

			return new ResponseEntity<>(this.categoryProductService.inventoryControl(emailId),
					HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}

	}

	// ==========================================================================================

	// 39. PRODUCT REFIL IN INVENTORY BY SELLER

	@PutMapping("/inventoryRefil")
	public ResponseEntity<String> inventoryRefil( @RequestBody InventoryRefil inventoryRefil)
			throws PotException {
		try {

			return new ResponseEntity<>(this.categoryProductService.inventoryRefil( inventoryRefil),
					HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}
	}

	// ==========================================================================================

	// 40. IF PRODUCT ARE NOT IN SELLER THEN DELETE THE PRODUCT By MANAGER

	@DeleteMapping("/removeProduct")
	public ResponseEntity<String> removeProductFromInventory(
			@RequestBody RemoveProduct removeProduct) throws PotException {
		try {

			return new ResponseEntity<>(this.categoryProductService.removeProductInInventory(removeProduct), HttpStatus.OK);
		} catch (Exception e) {
			throw new PotException(e.getMessage());
		}

	}

}

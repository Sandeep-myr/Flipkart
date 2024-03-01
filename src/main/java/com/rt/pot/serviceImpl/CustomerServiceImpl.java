package com.rt.pot.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rt.pot.error.DataGettingException;
import com.rt.pot.error.NotLogin;
import com.rt.pot.error.SavingException;
import com.rt.pot.model.Address;
import com.rt.pot.model.Customer;
import com.rt.pot.model.OrderDetails;
import com.rt.pot.model.OrderRecived;
import com.rt.pot.model.Product;
import com.rt.pot.modelReq.AddTocartDetails;
import com.rt.pot.modelReq.AddressReq;
import com.rt.pot.modelReq.CustomerReq;
import com.rt.pot.modelReq.OrderRequest;
import com.rt.pot.modelResponse.CustomerResponse;
import com.rt.pot.modelResponse.Order;
import com.rt.pot.modelResponse.Order.AddToCartResponse;
import com.rt.pot.modelResponse.Order.AddToCartResponse.ProductCart;
import com.rt.pot.orderEnum.OrderStatus;
import com.rt.pot.repository.CustomerRepo;
import com.rt.pot.repository.OrderRecievedRepo;
import com.rt.pot.repository.ProductsRepo;
import com.rt.pot.service.ICustomerService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private RequestToEntityMapping entityMapping;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private ProductsRepo productsRepo;

	@Autowired
	private OrderRecievedRepo orderRecievedRepo;

	@PersistenceContext
	private EntityManager entityManager;
	// ====================================================================================================

	// THIS METHOD IS RESPONSIBLE TO SAVING THE CUSTOMER REGISTRATION DATA IN
	// DATABASE

	@Override
	public ResponseEntity<String> customerRegistration(CustomerReq customerReq) throws SavingException {

		Customer customer = entityMapping.cutsomerReqToCustomerConverting(customerReq);
		try {
			this.customerRepo.save(customer);
		} catch (Exception e) {
			throw new SavingException(e.getMessage());
		}

		return new ResponseEntity<>("Customer Record Created Successfully", HttpStatus.OK);
	}

	// ==================================================================================================
	// GET THE CUSTOMER PROFILE DETAILS

	@Override
	public ResponseEntity<CustomerResponse> getCustomerDetails(Long mobileNumber, String emailId)
			throws DataGettingException {
		CustomerResponse custResponse=null;
		Customer customerLogin=null;
		
	
		try {
			Customer customer = emailId != null ? customerRepo.findByEmailIdIgnoreCase(emailId)
					: customerRepo.findByMobileNumber(mobileNumber);
			custResponse = new CustomerResponse();
			custResponse.setCustomerName(customer.getCustomerName());
			custResponse.setEmailId(customer.getEmailId());
			custResponse.setMobileNumber(customer.getMobileNumber());

		} catch (Exception e) {
			throw new DataGettingException(e.getMessage());
		}
		
		return new ResponseEntity<>(custResponse, HttpStatus.OK);
	}

	// ===================================================================================================

	// CHANGE THE CUSTOMER PASSWORD AFTER THE CUSTOMER VERIFICATION

	@Override
	public ResponseEntity<String> changePassword(String oldPassword, String emailId, String newPassword)
			throws DataGettingException {

		

	

		Customer customer = customerRepo.findByEmailIdIgnoreCase(emailId);
		try {
			if (customer != null) {

				if (customer.getPassword().equals(oldPassword)) {
					customer.setPassword(newPassword);
					customerRepo.save(customer);
				} else {
					throw new DataGettingException("Invalid Credentials");
				}
			}
		} catch (Exception e) {
			throw new DataGettingException(e.getMessage());

		}
		return new ResponseEntity<>("Password Updated Successfully ", HttpStatus.OK);
	}
	// ===================================================================================================

	// CUSTOMER LOGIN VERIFICATION METHOD

	@Override
	public ResponseEntity<CustomerResponse> customerLoginVerifiaction(String emailId, Long mobileNumber,
			String password) throws DataGettingException {
		CustomerResponse customerResponse = null;
		Customer customer = emailId != null ? customerRepo.findByEmailIdIgnoreCase(emailId)
				: customerRepo.findByMobileNumber(mobileNumber);

		try {
			if (customer != null) {

				if (customer.getPassword().equals(password)) {
					customerResponse = new CustomerResponse();
					customerResponse.setCustomerName(customer.getCustomerName());
					customerResponse.setEmailId(customer.getEmailId());
					customerResponse.setMobileNumber(customer.getMobileNumber());
				
					
				} else {
					throw new DataGettingException("Invalid Credentials");
				}
			} else {
				throw new DataGettingException("EmailId Does Not Exist");
			}

		} catch (Exception e) {
			throw new DataGettingException(e.getMessage());
		}

		return new ResponseEntity<>(customerResponse, HttpStatus.OK);
	}

	// ===================================================================================================

	// CUSTOMER Adding The prodct Into the cart
	public Map<String, List<AddTocartDetails>> addToCart = new HashMap<>();
//
//	@Override
//	public ResponseEntity<String> addToCart(String emailId, HttpSession httpSession, OrderRequest orderReq,
//			Integer productId) throws DataGettingException {
//
//		Customer customer = (Customer) httpSession.getAttribute(emailId.toUpperCase());
//
//		if (customer != null) {
//			AddTocartDetails addTocartDetails = new AddTocartDetails();
//
//			Product product = productsRepo.findById(productId).get();
//			if (product.getProductQty() >= orderReq.getProductQty()) {
//				if (addToCart.containsKey(customer.getEmailId()) && product != null) {
//					System.out.println("Kart me kuch hai");
//
//
//                       for(AddTocartDetails p :addToCart.get(customer.getEmailId())) {
//						if (p.getProduct().getProductId() == productId) {
//							System.out.println("Kart me same product add kar rahe ");
//
//							p.setProductQty(orderReq.getProductQty());
//
//							Double totalPrice = p.getProduct().getProductPrice() * orderReq.getProductQty();
//							Double discount = p.getProduct().getDiscountPercentage();
//							Double discountAmount = (totalPrice * discount) / 100;
//							Double payAmount = totalPrice - discountAmount;
//							p.setTotalPrice(totalPrice);
//
//							p.setDiscount(discountAmount);
//							p.setPayableAmount(payAmount);
//						} else {
//							System.out.println("Kart me kuch hai lekin different product add kar rahe");
//							addTocartDetails.setProduct(product);
//							addTocartDetails.setProductQty(orderReq.getProductQty());
//
//							Double totalPrice = product.getProductPrice() * orderReq.getProductQty();
//							Double discount = product.getDiscountPercentage();
//							Double discountAmount = (totalPrice * discount) / 100;
//							Double payAmount = totalPrice - discountAmount;
//							addTocartDetails.setTotalPrice(totalPrice);
//
//							addTocartDetails.setDiscount(discountAmount);
//							addTocartDetails.setPayableAmount(payAmount);
//                            addToCart.get(customer.getEmailId()).add(addTocartDetails);
//						}
//					}
//
//				} else {
//					System.out.println("Kart khali hai pehla product add kar rahe");
//
//					addToCart.put(customer.getEmailId(), new ArrayList<AddTocartDetails>());
//
//					addTocartDetails.setProduct(product);
//
//					addTocartDetails.setProductQty(orderReq.getProductQty());
//					Double totalPrice = addTocartDetails.getProduct().getProductPrice() * orderReq.getProductQty();
//					Double discount = addTocartDetails.getProduct().getDiscountPercentage();
//					Double discountAmount = (totalPrice * discount) / 100;
//					Double payAmount = totalPrice - discountAmount;
//					addTocartDetails.setTotalPrice(totalPrice);
//
//					addTocartDetails.setDiscount(discountAmount);
//					addTocartDetails.setPayableAmount(payAmount);
//					
//					addToCart.get(customer.getEmailId()).add(addTocartDetails);
//				}
//			} else {
//				throw new DataGettingException("OUT OF STOCK!!!!!!!!!");
//			}
//		} else {
//			throw new NotLogin("Without Login You Can't add The product");
//		}
//
//		System.out.println(addToCart);
//
//		return ResponseEntity.ok("Product added to cart successfully.");
//	}

	
	
	@Override
	public ResponseEntity<String> addToCart(String emailId, OrderRequest orderReq, Integer productId) throws DataGettingException {
		
		
	    Customer customer = customerRepo.findByEmailIdIgnoreCase(emailId.toUpperCase());

	   

	    Product product = productsRepo.findById(productId).orElseThrow(() -> new DataGettingException("Product not found!"));

	    if (product.getProductQty() < orderReq.getProductQty()) {
	        throw new DataGettingException("OUT OF STOCK!!!!!!!!!");
	    }

	    addToCart.computeIfAbsent(customer.getEmailId(), k -> new ArrayList<>());

	    this.addToCartDetails(product, orderReq.getProductQty(), customer.getEmailId());

	    System.out.println(addToCart);

	    return ResponseEntity.ok("Product added to cart successfully.");
	}

	private  void addToCartDetails(Product product, int productQty, String emailId) {
		
	    boolean productExistsInCart = false;
	    for (AddTocartDetails p : addToCart.get(emailId)) {
	   
	        if (p.getProduct().getProductId().equals(product.getProductId())) {
	            productExistsInCart = true;
	            System.out.println("Kart me same product add kar rahe ");
	            p.setProductQty(productQty);

	            double totalPrice = p.getProduct().getProductPrice() * productQty;
	            double discount = p.getProduct().getDiscountPercentage();
	            double discountAmount = (totalPrice * discount) / 100;
	            double payAmount = totalPrice - discountAmount;

	            p.setTotalPrice(totalPrice);
	            p.setDiscount(discountAmount);
	            p.setPayableAmount(payAmount);
	            break;
	        }
	    }

	    if (!productExistsInCart) {
	        System.out.println("Kart me kuch hai lekin different product add kar rahe");
	        AddTocartDetails addTocartDetails = new AddTocartDetails();
	        addTocartDetails.setProduct(product);
	        addTocartDetails.setProductQty(productQty);

	        double totalPrice = product.getProductPrice() * productQty;
	        double discount = product.getDiscountPercentage();
	        double discountAmount = (totalPrice * discount) / 100;
	        double payAmount = totalPrice - discountAmount;

	        addTocartDetails.setTotalPrice(totalPrice);
	        addTocartDetails.setDiscount(discountAmount);
	        addTocartDetails.setPayableAmount(payAmount);

	        addToCart.get(emailId).add(addTocartDetails);
	    }
	 
	    
	    System.out.println(addToCart);
	}

	// ================================================================================================================

	// VIEW THE CART WHAT IS CUSTOMER ADDED INTO THE CART

	@Override
	public ResponseEntity<AddToCartResponse> viewCart(String emailId)
			throws DataGettingException {

		Customer customer = customerRepo.findByEmailIdIgnoreCase(emailId.toUpperCase());

		Order order = new Order();
		Order.AddToCartResponse addToCartResponse = order.new AddToCartResponse();
		

			if (addToCart.containsKey(emailId)) {
				Double totalBillingAmount = 0.0;
				Double totalSavingAmount = 0.0;
				Double totalPayableAmount = 0.0;
				List<ProductCart> productCartList = new ArrayList<>();
				for (AddTocartDetails product : addToCart.get(emailId)) {
					AddToCartResponse.ProductCart productCart = addToCartResponse.new ProductCart();
					productCart.setProductId(product.getProduct().getProductId());
					productCart.setProductName(product.getProduct().getProductName());
					productCart.setBrandName(product.getProduct().getBrandName());
					productCart.setDescription(product.getProduct().getDescription());
					productCart.setImageUrl(product.getProduct().getImageUrl());
					productCart.setSellerName(product.getProduct().getAdminData().getAdminName());
					productCart.setProductQty(product.getProductQty());
					productCart.setProductPrice(product.getProduct().getProductPrice());
					productCart.setTotalProductPurchasePrice(product.getTotalPrice());
					productCart.setDiscountAmount(product.getDiscount());
					productCart.setAfterDiscountAmount(product.getPayableAmount());
					productCartList.add(productCart);

					totalPayableAmount += product.getPayableAmount();
					totalSavingAmount += product.getDiscount();
					totalBillingAmount += product.getTotalPrice();

				}
				addToCartResponse.setProductCartList(productCartList);
				addToCartResponse.setTotalPayableAmount(totalPayableAmount);
				addToCartResponse.setTotalSaving(totalSavingAmount);
				addToCartResponse.setTotalBillAmount(totalBillingAmount);
			} else {

				throw new DataGettingException("No Items Are Added Into Cart");
			}
		

		return new ResponseEntity<>(addToCartResponse, HttpStatus.OK);
	}

	// ==================================================================================

	// CHECK OUT THE ITEM AND PAY BILL AND UPDATE THE INVENTORY
	@Transactional
	@Override
	public ResponseEntity<String> checkOutCart(String emailId, AddressReq addressReq)
			throws DataGettingException {
		Customer customer = customerRepo.findByEmailIdIgnoreCase(emailId.toUpperCase());

		
			if (addToCart.containsKey(emailId)) {
				OrderRecived orderRecived = new OrderRecived();
				orderRecived.setDateTime(LocalDateTime.now());

				Double totalSaving = 0.0;
				Double totalBill = 0.0;
				Double totalPay = 0.0;
				List<OrderDetails> orderDetailsList = new ArrayList<>();
				for (AddTocartDetails product : addToCart.get(customer.getEmailId())) {
					totalBill += product.getTotalPrice();
					totalSaving += product.getDiscount();

					OrderDetails orderDetails = new OrderDetails();
					orderDetails.setAdmin(product.getProduct().getAdminData());
					orderDetails.setProduct(product.getProduct());
					orderDetails.setProductQty(product.getProductQty());
					orderDetails.setOrderStatus(OrderStatus.ORDER_RECIEVED);

					// Set the reference to the OrderRecived entity
					orderDetails.setOrderRecived(orderRecived);

					System.out.println(product.getProduct().getProductQty()+" "+product.getProductQty());
					System.out.println(product.getProduct().getProductQty()-product.getProductQty());
					
					productsRepo.updateProductQty(product.getProduct().getProductQty() - product.getProductQty(),
							product.getProduct().getProductId());

					orderDetailsList.add(orderDetails);
				}
				totalPay = totalBill - totalSaving;
				Address address = MapperOfEntity.AddressReqToAddress(addressReq, new Address());

				// Persist the Address entity
				entityManager.persist(address);

				orderRecived.setAddress(address);
				// Set the Customer entity from the database (managed state)

				orderRecived.setCustomer(customer);

				orderRecived.setOrderDetails(orderDetailsList);
				orderRecived.setAmountPay(totalPay);
				orderRecived.setTotalPrice(totalBill);
				orderRecived.setTotalSaving(totalSaving);

				List<OrderRecived> orderReciveds = new ArrayList<>();
				orderReciveds.add(orderRecived);

				customer.setOrderRecived(orderReciveds);

				customerRepo.save(customer);
				// Save the OrderRecived entity
				orderRecievedRepo.save(orderRecived);

				addToCart.remove(customer.getEmailId());
			} else {
				throw new DataGettingException("No Items Are Added Into Cart");
			}
		
		return new ResponseEntity<>("Checkout Successfully", HttpStatus.OK);
	}

	// ===================================================================================================================

	// SEE THE ORDER HISTORY PRODUCT PURCHASE BY THE USER

	@Override
	public List<Order> orderHistory(String emailId) {

		List<Order> orderListHistory = new ArrayList<>();
		Customer customer = customerRepo.findByEmailIdIgnoreCase(emailId.toUpperCase());
		Order orderDetailsWithAddress;

			List<OrderRecived> orderRecieved = orderRecievedRepo.findByCustomer(customer);

//--------------------------------------
			// Conversion

			for (OrderRecived order : orderRecieved) {
				orderDetailsWithAddress = new Order();
				Order.AddToCartResponse addToCartResponse = orderDetailsWithAddress.new AddToCartResponse();

				// -----------ProductDetails
				List<ProductCart> productDetailsList = new ArrayList<>();
				order.getOrderDetails().stream().forEach(product -> {
					int i = 0;
					i++;
					System.err.println(i);
					// YE HO GAI EK PERTICULAR ORDER ME ADD KIYE GAYE PRODUCT KI DETAILS
					AddToCartResponse.ProductCart productCart = addToCartResponse.new ProductCart();
					productCart.setProductName(product.getProduct().getProductName());
					productCart.setBrandName(product.getProduct().getBrandName());
					productCart.setDescription(product.getProduct().getDescription());
					productCart.setProductQty(product.getProductQty());
					productCart.setImageUrl(product.getProduct().getImageUrl());
					productCart.setOrderId(product.getOrderId());
					Double totalPrice = product.getProduct().getProductPrice() * product.getProductQty();
					Double discountAmount = product.getProduct().getDiscountPercentage() * totalPrice / 100;
					productCart.setDiscountAmount(discountAmount);
					productCart.setProductPrice(product.getProduct().getProductPrice());
					productCart.setSellerName(product.getAdmin().getAdminName());
					productCart.setTotalProductPurchasePrice(totalPrice);
					productCart.setAfterDiscountAmount(totalPrice - discountAmount);
					productDetailsList.add(productCart);
				});
				// US PERTICULAR ORDER ME PAY BILL KI SUMMARY
				addToCartResponse.setProductCartList(productDetailsList);
				addToCartResponse.setTotalBillAmount(order.getTotalPrice());
				addToCartResponse.setTotalSaving(order.getTotalSaving());
				addToCartResponse.setTotalPayableAmount(order.getAmountPay());

				// US PERTICULAE ORDER KO SHIPPED KARNE KA ADDRESS
				Address address = order.getAddress();
				AddressReq addressReq = new AddressReq();

				addressReq.setCity(address.getCity());
				addressReq.setStreet(address.getStreet());
				addressReq.setState(address.getState());
				addressReq.setCountry(address.getCountry());
				addressReq.setZipCode(address.getZipCode());

				// SHIPPING ADDRESS AUR ORDER DETAILS ADD KARNE K BAAD ORDER COMPLETE HO GAYA
				orderDetailsWithAddress.setOrderDate(order.getDateTime());
				orderDetailsWithAddress.setAddressReq(addressReq);
				orderDetailsWithAddress.setAddToCartResponse(addToCartResponse);

				orderListHistory.add(orderDetailsWithAddress);
			}

		
		return orderListHistory;
	}
	
	
	
	

}

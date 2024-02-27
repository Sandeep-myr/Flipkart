package com.rt.pot.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rt.pot.emailService.EmailServiceProvider;
import com.rt.pot.error.DataGettingException;
import com.rt.pot.error.EmailIdAreAlreadyExist;
import com.rt.pot.error.EmailIdDoesNotExist;
import com.rt.pot.error.InvalidCredentials;
import com.rt.pot.error.NotLogin;
import com.rt.pot.mailTemplate.MailTemplate;
import com.rt.pot.model.Address;
import com.rt.pot.model.Admin;
import com.rt.pot.model.Contacts;
import com.rt.pot.model.LoginDetails;
import com.rt.pot.model.OrderDetails;
import com.rt.pot.modelReq.AddressReq;
import com.rt.pot.modelReq.AdminReq;
import com.rt.pot.modelReq.ContactsReq;
import com.rt.pot.modelResponse.AdminResponse;
import com.rt.pot.modelResponse.EntityToDtoMapping;
import com.rt.pot.modelResponse.SellerOrderRecieved;
import com.rt.pot.modelResponse.SellerOrderRecieved.ProductDetails;
import com.rt.pot.orderEnum.OrderStatus;
import com.rt.pot.repository.AdminRepo;
import com.rt.pot.repository.ContactsRepo;
import com.rt.pot.repository.LoginDetailsRepo;
import com.rt.pot.repository.OrderRepo;
import com.rt.pot.service.IAdminService;
import com.rt.pot.util.AdminOrderUtilityManager;

import io.swagger.v3.oas.annotations.info.Contact;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@Service
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private ContactsRepo contactsRepo;

	@Autowired
	private EmailServiceProvider emailServiceProvider;

	@Autowired
	private MailTemplate mailTemplate;

	@Autowired
	private LoginDetailsRepo loginRepo;

	@Override
	public String registerAdmin(Admin admin) throws MessagingException {

		boolean isExist = false;
		for (Contacts contact : admin.getAdminContacts()) {
			if (contactsRepo.existsByEmailId(contact.getEmailId())) {
				isExist = true;
				break;
			}

		}
		if (isExist) {
			throw new EmailIdAreAlreadyExist("This Email Id Are Already Exist ");
		} else {
			String[] reciepents = new String[] {};

			for (Contacts contact : admin.getAdminContacts()) {
				reciepents = Arrays.copyOf(reciepents, reciepents.length + 1);
				reciepents[reciepents.length - 1] = contact.getEmailId();

			}
			// sending the Message to candidate we have to recive your mail regarding to
			// your intrest

			this.emailServiceProvider.sendMail(reciepents, this.mailTemplate.registrationTemplate(admin),
					"Your Request Has Accept");
			this.adminRepo.save(admin);

			return "Admin Registered Successfully";
		}
	}

	// =====================================================================================================================================

	// ADMIN LOGIN VERIFICATION STATUS IS HERE

	@Override
	public ResponseEntity<AdminResponse> login(String emailId, String password)
			throws EmailIdDoesNotExist, InvalidCredentials {
		AdminResponse adminResponse = new AdminResponse();
		if (!loginRepo.existsByUserEmailId(emailId)) {
			throw new EmailIdDoesNotExist("This Email Id Are Does Not Exist !!!");
		} else if (loginRepo.findByUserEmailIdIgnoreCase(emailId).getPassword().equals(password)) {

			// CONVERTING ADMIN TO ADMIN RESPONSE;

			Admin admin = loginRepo.findByUserEmailIdIgnoreCase(emailId).getAdmin();

			adminResponse.setAdminAddresses(EntityToDtoMapping.addressEntityToAddressReq(admin.getAdminAddresses()));
			adminResponse.setAdminContacts(EntityToDtoMapping.contactsEntityToContactsReq(admin.getAdminContacts()));
			adminResponse.setAdminName(admin.getAdminName());
			adminResponse.setIsApproved(admin.getIsApproved());

					} else {
			throw new InvalidCredentials("This UserName and Password Are does not exist !!!!!!!");
		}
		return ResponseEntity.ok(adminResponse);
	}

	// =======================================================================================================================

	// UPDATE THE ADMIN DETAILS BASED ON THE ADMIN EMAILID

	@Override
	public ResponseEntity<String> updateAdminDetails(String emailId, AdminReq adminReq) {
			LoginDetails loginDetails = loginRepo.findByUserEmailIdIgnoreCase(emailId);

			if (adminReq.getAdminName() != null) {
				loginDetails.getAdmin().setAdminName(adminReq.getAdminName());

				if (adminReq.getAdminAddresses() != null) {

					Set<Address> addressSet = loginDetails.getAdmin().getAdminAddresses();
					List<Address> addressList = new ArrayList<>(addressSet);
					Set<AddressReq> newUpdated = adminReq.getAdminAddresses();
					List<AddressReq> addressReq = new ArrayList<>(newUpdated);
					System.out.println(addressSet);
					System.out.println(newUpdated);
					for (int i = 0; i < addressList.size(); i++) {

						addressList.get(i).setStreet(addressReq.get(i).getStreet());
						addressList.get(i).setState(addressReq.get(i).getState());
						addressList.get(i).setCity(addressReq.get(i).getCity());
						addressList.get(i).setCountry(addressReq.get(i).getCountry());
						addressList.get(i).setZipCode(addressReq.get(i).getZipCode());

					}

					if (adminReq.getAdminContacts() != null) {
						Set<Contacts> contactSet = loginDetails.getAdmin().getAdminContacts();
						List<Contacts> contactList = new ArrayList<>(contactSet);

						Set<ContactsReq> newContact = adminReq.getAdminContacts();
						List<ContactsReq> contactReq = new ArrayList<>(newContact);

						for (int i = 0; i < contactList.size(); i++) {

							contactList.get(i).setMobileNumber(contactReq.get(i).getMobileNumber());

						}

					}

				}

			}
			adminRepo.save(loginDetails.getAdmin());

		
		return new ResponseEntity<>("Details Are updated successfully", HttpStatus.OK);
	}

	// ========================================================================================================

	// PENDING
	// HOW MANY ORDER RECIEVE BY SELLER SEE ALL THE DETAILS OF ORDER

//	Map<Integer,List<SellerOrderRecieved>> orderMap= new HashMap<>();
	@Override
	public List<SellerOrderRecieved> getOrderDetails(String emailId)
			throws DataGettingException {
		List<SellerOrderRecieved> sellerorderList = new ArrayList<>();
		
		Admin admin = contactsRepo.findAllByEmailId(emailId).getAdminData();
		
			List<OrderDetails> orderDetailsList = orderRepo.findByAdmin(admin);
			Set<Integer> orderRecivedIdForPerticularSeller = new HashSet<>();

			for (OrderDetails order : orderDetailsList) {
				orderRecivedIdForPerticularSeller.add(order.getOrderRecived().getOrderRecivedId());
				System.out.println(order.getOrderId());
			}

			System.out.println(orderRecivedIdForPerticularSeller);
			System.out.println(orderDetailsList.size());

			if (!orderRecivedIdForPerticularSeller.isEmpty()) {

				OrderDetails orderSummarycopy = null;

				for (Integer orderRecivedId : orderRecivedIdForPerticularSeller) {
					SellerOrderRecieved sellerOrderRecieved = new SellerOrderRecieved();
					List<ProductDetails> productDetailsListAccordingToOrderRecivedId = new ArrayList<>();
					Double totalPayableAmount = 0.0;
					Double totalDiscountAmount = 0.0;
					Double totalBillAmount = 0.0;

					for (OrderDetails orderSummary : orderDetailsList) {

						if (orderSummary.getOrderRecived().getOrderRecivedId() == orderRecivedId) {
							orderSummarycopy = orderSummary;
							Double totalProductPrice = orderSummary.getProductQty()
									* orderSummary.getProduct().getProductPrice();

							Double discountPrice = totalProductPrice * orderSummary.getProduct().getDiscountPercentage()
									/ 100;

							Double totalPayingAmount = totalProductPrice - discountPrice;
							ProductDetails productDetails = AdminOrderUtilityManager.productDetailsSetting(orderSummary,
									totalPayingAmount, discountPrice, totalProductPrice, sellerOrderRecieved);

							// ===========================SET TOTAL BILL AMOUNT
							totalPayableAmount += totalPayingAmount;
							totalDiscountAmount += discountPrice;
							totalBillAmount += totalProductPrice;

							productDetailsListAccordingToOrderRecivedId.add(productDetails);
						}
					}
					sellerOrderRecieved.setOrderRecievedId(orderSummarycopy.getOrderRecived().getOrderRecivedId());
					sellerOrderRecieved.setProductDetailsList(productDetailsListAccordingToOrderRecivedId);

					sellerOrderRecieved.setTotalBillAmount(totalBillAmount); // BAKI SAARI CHEEZE SET HUI
					sellerOrderRecieved.setTotalPayableAmount(totalPayableAmount);
					sellerOrderRecieved.setTotalSaving(totalDiscountAmount);
					sellerOrderRecieved.setOrderDate(orderSummarycopy.getOrderRecived().getDateTime());
					sellerOrderRecieved.setAddressResponse(EntityToDtoMapping
							.AddressToAddressResponse(orderSummarycopy.getOrderRecived().getAddress()));
					sellerOrderRecieved.setCustomerResponse(EntityToDtoMapping
							.customerToCustomerResponse(orderSummarycopy.getOrderRecived().getCustomer()));
					sellerorderList.add(sellerOrderRecieved);
				}
				System.out.println(sellerorderList);
			} else {
				throw new DataGettingException("No Order Found");
			}

				return sellerorderList;
	}

	// ==========================================================================================================

	// Admin Has Change The Order Status According To Order Like

	@Override
	public String orderStatusUpdate(String emailId, Integer statusCode, Integer orderRecivedId) {

		OrderStatus orderStatus = null;
		
		Admin admin = contactsRepo.findAllByEmailId(emailId).getAdminData();
		
			List<OrderDetails> orderDetailsList = orderRepo.findByAdmin(admin);

			for (OrderDetails orderDetails : orderDetailsList) {
				if (orderDetails.getOrderRecived().getOrderRecivedId() == orderRecivedId) {
					switch (statusCode) {
					case 1: {
						orderDetails.setOrderStatus(OrderStatus.ORDER_SHIPPED);
						orderStatus = OrderStatus.ORDER_SHIPPED;
						break;
					}
					case 2: {
						orderDetails.setOrderStatus(OrderStatus.ORDER_DISPATCHED);
						orderStatus = OrderStatus.ORDER_DISPATCHED;
						break;
					}
					case 3: {
						orderDetails.setOrderStatus(OrderStatus.ORDER_ON_THE_WAY);
						orderStatus = OrderStatus.ORDER_ON_THE_WAY;
						break;
					}
					case 4: {
						orderDetails.setOrderStatus(OrderStatus.ORDER_DELIVERED);
						orderStatus = OrderStatus.ORDER_DELIVERED;
						break;
					}
					default: {
						System.out.println("Not A valid Entry");
					}
					}
					orderRepo.save(orderDetails);
				}
			}

				return "Order Status is Updated  " + orderStatus;
	}

}

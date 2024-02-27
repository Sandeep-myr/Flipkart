package com.rt.pot.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rt.pot.error.DataGettingException;
import com.rt.pot.error.SavingException;
import com.rt.pot.modelReq.AddressReq;
import com.rt.pot.modelReq.CustomerReq;
import com.rt.pot.modelReq.OrderRequest;
import com.rt.pot.modelResponse.CustomerResponse;
import com.rt.pot.modelResponse.Order;

import jakarta.servlet.http.HttpSession;

public interface ICustomerService {

	ResponseEntity<String> customerRegistration(CustomerReq customerReq) throws SavingException;

	ResponseEntity<CustomerResponse> getCustomerDetails(Long mobileNumber , String emailId) throws DataGettingException;

	ResponseEntity<String> changePassword(String oldPassword, String emailId, String newPassword) throws DataGettingException;

	ResponseEntity<CustomerResponse> customerLoginVerifiaction(String emailId, Long mobileNumber, String password) throws DataGettingException;

	ResponseEntity<String> addToCart(String emailId, OrderRequest orderReq, Integer productId) throws DataGettingException;

	ResponseEntity<Order.AddToCartResponse> viewCart(String emailId) throws DataGettingException;

	ResponseEntity<String> checkOutCart(String emailId,AddressReq addressReq) throws DataGettingException;

	List<Order> orderHistory(String emailId);

}

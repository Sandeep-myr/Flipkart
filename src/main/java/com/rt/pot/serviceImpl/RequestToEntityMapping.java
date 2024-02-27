package com.rt.pot.serviceImpl;

import org.springframework.stereotype.Component;

import com.rt.pot.model.Customer;
import com.rt.pot.modelReq.CustomerReq;

@Component
public class RequestToEntityMapping {

	

	public Customer cutsomerReqToCustomerConverting(CustomerReq customerReq) {
		
		Customer customer = new Customer();
		
		customer.setCustomerName(customerReq.getCustomerName());
		customer.setEmailId(customerReq.getEmailId());
		customer.setMobileNumber(customerReq.getMobileNumber());
		customer.setPassword(customerReq.getPassword());
		
		return customer; 
		
	}
}

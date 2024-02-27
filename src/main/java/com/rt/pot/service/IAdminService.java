package com.rt.pot.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rt.pot.error.DataGettingException;
import com.rt.pot.error.EmailIdDoesNotExist;
import com.rt.pot.error.InvalidCredentials;
import com.rt.pot.model.Admin;
import com.rt.pot.modelReq.AdminReq;
import com.rt.pot.modelResponse.AdminResponse;
import com.rt.pot.modelResponse.SellerOrderRecieved;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

public interface IAdminService {

	public String registerAdmin(Admin admin) throws MessagingException;

	
	public ResponseEntity<AdminResponse> login(String emailId, String password)
			throws EmailIdDoesNotExist, InvalidCredentials;


	public ResponseEntity<String> updateAdminDetails(String emailId, AdminReq adminReq);


	public List<SellerOrderRecieved> getOrderDetails(String emailId) throws DataGettingException;


	public String orderStatusUpdate(String emailId, Integer statusCode, Integer orderRecivedId);



	
	
}

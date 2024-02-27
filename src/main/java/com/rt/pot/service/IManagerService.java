package com.rt.pot.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rt.pot.error.InvalidCredentials;
import com.rt.pot.model.Manager;
import com.rt.pot.modelReq.ManagerReq;
import com.rt.pot.modelResponse.AdminResponse;
import com.rt.pot.modelResponse.ManagerResponse;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

public interface IManagerService {

   public String registerManager(Manager manager);

public List<AdminResponse> findAllAdminRequest();

public ResponseEntity<List<AdminResponse>> pendingRequest();

public ResponseEntity<String> approveRequest(String emailId, boolean action) throws MessagingException;

public ResponseEntity<String> deletAdminRecordByManager(String emailId, String managerEmailId) throws MessagingException;

public ManagerResponse managerLogin(ManagerReq managerReq) throws InvalidCredentials;




}

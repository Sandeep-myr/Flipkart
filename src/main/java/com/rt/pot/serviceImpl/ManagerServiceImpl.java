package com.rt.pot.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rt.pot.emailService.EmailServiceProvider;
import com.rt.pot.error.InvalidCredentials;
import com.rt.pot.error.NotLogin;
import com.rt.pot.mailTemplate.MailTemplate;
import com.rt.pot.model.Address;
import com.rt.pot.model.Admin;
import com.rt.pot.model.Contacts;
import com.rt.pot.model.LoginDetails;
import com.rt.pot.model.Manager;
import com.rt.pot.modelReq.ManagerReq;
import com.rt.pot.modelResponse.AdminResponse;
import com.rt.pot.modelResponse.EntityToDtoMapping;
import com.rt.pot.modelResponse.ManagerResponse;
import com.rt.pot.repository.AddressRepo;
import com.rt.pot.repository.AdminRepo;
import com.rt.pot.repository.ContactsRepo;
import com.rt.pot.repository.LoginDetailsRepo;
import com.rt.pot.repository.ManagerRepo;
import com.rt.pot.service.IManagerService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@Service
public class ManagerServiceImpl implements IManagerService {

	@Autowired
	private ManagerRepo managerRepo;

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private ContactsRepo contactsRepo;

	@Autowired
	private LoginDetailsRepo loginDetailsRepo;

	@Autowired
	private EmailServiceProvider emailServiceProvider;

	@Autowired
	private MailTemplate mailTemplate;

	@Autowired
	private AddressRepo addressRepo;

	@Override
	public String registerManager(Manager manager) {
		managerRepo.save(manager);
		return "Manager Registered Successfully";
	}

	@Override
	public List<AdminResponse> findAllAdminRequest() {

		List<Admin> adminList1 = adminRepo.findAll();

		List<AdminResponse> adminList = EntityToDtoMapping.EntityToDtoConvertor(adminList1);

		return adminList;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<List<AdminResponse>> pendingRequest() {
		List<Admin> adminList1 = adminRepo.findByIsApproved("No");
		List<AdminResponse> adminList = EntityToDtoMapping.EntityToDtoConvertor(adminList1);
		return new ResponseEntity(adminList, HttpStatus.OK);
	}

//	=======================================================================================================================================
	// APPROVAL OR REJECTION OF REQUEST WHO'S CANDIDATE ARE APPLY FOR ADMIN ROLE

	@Override
	public ResponseEntity<String> approveRequest(String emailId, boolean action) throws MessagingException {
		Contacts contact = contactsRepo.findAllByEmailId(emailId);
		if (action) {
			String password = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

			adminRepo.updateStatus(contact.getAdminData().getAdminId(), "Approved");

			// ==============================================================================================================================
			// Setting The User name password Into LoginDetails Table after Approval
			LoginDetails loginDetails = new LoginDetails();
			loginDetails.setUserEmailId(emailId);
			loginDetails.setPassword(password);
			loginDetails.setAdmin(contact.getAdminData());
			loginDetailsRepo.save(loginDetails);

//    	  =============================================================================================================================

			// AFTER SAVING THE DATA SEND MAIL TO CANDIDATE NOW YOU ARE ADMIN AND SEND
			// USERNAME AND PASSWORD.

			String template = this.mailTemplate.approvalOfAdminTemplate(contact.getAdminData().getAdminName(), emailId,
					password);
			this.emailServiceProvider.sendMail(new String[] { emailId }, template,
					"Status Updated Of Your Application");

		} else {

			adminRepo.updateStatus(contact.getAdminData().getAdminId(), "Reject");

			// ==========================================================================================================================
			// AFTER THE REJECTION SENE THE REGRETION MAIL TO CANDIDATE

			String template = this.mailTemplate.rejectionOfAdminTemplate(contact.getAdminData().getAdminName());
			this.emailServiceProvider.sendMail(new String[] { emailId }, template,
					"Status Updated Of Your Application");
		}
		return new ResponseEntity<>("Status is Updated", HttpStatus.OK);
	}

	// =================================================================================================

	// DELETE THE ADMIN RECORD USING ADMIN

	@Override
	public ResponseEntity<String> deletAdminRecordByManager(String emailId,
			String managerEmailId) throws MessagingException {
		Manager manager = managerRepo.findByEmailIdIgnoreCase(managerEmailId.toUpperCase());
		

			LoginDetails loginDetails = loginDetailsRepo.findByUserEmailIdIgnoreCase(emailId);

			System.out.println(loginDetails);
			// delete form the Admin Table
			adminRepo.deleteById(loginDetails.getAdmin().getAdminId());

			// delete from the address table

			List<Address> addressList = addressRepo.findByAdminData(loginDetails.getAdmin());

			addressRepo.deleteAll(addressList);

			// delete from the contacts table

			List<Contacts> contactList = contactsRepo.findByAdminData(loginDetails.getAdmin());

			contactsRepo.deleteAll(contactList);

			// delete record from LoginDetails table

			loginDetailsRepo.deleteByUserEmailId(emailId);

			// NOW SENDING THE MAIL YOU ACCOUNT HAS BEEN TERMINATED PERMANENTLY

			emailServiceProvider.sendMail(new String[] { emailId },
					mailTemplate.accountDeleteOfAdminTemplate(loginDetails.getAdmin().getAdminName()),
					"Account Has Beean Deleted");

		
		return new ResponseEntity<>("Account Has Beean deleted", HttpStatus.OK);
	}

//	=======================================================================================================================================

	// MANAGER LOGIN SYSTEM()

	@Override
	public ManagerResponse managerLogin(ManagerReq managerReq) throws InvalidCredentials {
		ManagerResponse managerResponse = null;

		Manager manager = managerRepo.findByEmailIdIgnoreCase(managerReq.getUserName());

		if (manager != null && managerReq.getPassword().equals(manager.getPassword())) {
			managerResponse = new ManagerResponse();

			managerResponse.setManagerName(manager.getManagerName());

			managerResponse.setMobileNumber(manager.getMobileNumber());
			managerResponse.setEmailId(manager.getEmailId());
			managerResponse.setStreet(manager.getStreet());
			managerResponse.setCity(manager.getCity());
			managerResponse.setState(manager.getState());
			managerResponse.setCountry(manager.getCountry());
			managerResponse.setZipCode(manager.getZipCode());
			managerResponse.setUserName(manager.getManagerName());

			

		} else {
			throw new InvalidCredentials("Invalid Credentials");
		}

		return managerResponse;
	}

}

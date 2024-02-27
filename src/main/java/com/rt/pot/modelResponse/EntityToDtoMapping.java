package com.rt.pot.modelResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.rt.pot.model.Address;
import com.rt.pot.model.Admin;
import com.rt.pot.model.Contacts;
import com.rt.pot.model.Customer;
import com.rt.pot.modelReq.AddressReq;
import com.rt.pot.modelReq.ContactsReq;

import lombok.Data;

@Data
public class EntityToDtoMapping {

	
	public static List<AddressReq> addressEntityToAddressReq(Set<Address> address  ) {
		List<AddressReq> addressReqList= new ArrayList<>();
		address.stream().forEach(Address->{
		AddressReq addressReq= new  AddressReq();
			addressReq.setCity(Address.getCity());
			addressReq.setStreet(Address.getStreet());
			addressReq.setCountry(Address.getCountry());
			addressReq.setState(Address.getState());
			addressReq.setZipCode(Address.getZipCode());
			
			addressReqList.add(addressReq);
			
		});
		
		
		
		
		return addressReqList;
	}
	
	public static List<ContactsReq> contactsEntityToContactsReq(Set<Contacts> contacts ) {
		 List<ContactsReq> contactsReqList= new ArrayList<>();
		contacts.stream().forEach(Contacts->{
			ContactsReq contactsReq= new ContactsReq();
			contactsReq.setMobileNumber(Contacts.getMobileNumber());
			contactsReq.setEmailId(Contacts.getEmailId());
			contactsReqList.add(contactsReq);
		});
		 
		
		return contactsReqList;
	}
	
	
	public static List<AdminResponse> EntityToDtoConvertor(List<Admin> adminList1) {
		List<AdminResponse> adminList = new ArrayList<>();
		for (Admin admin : adminList1) {
			AdminResponse admin2 = new AdminResponse();

			admin2.setAdminAddresses(EntityToDtoMapping.addressEntityToAddressReq(admin.getAdminAddresses()));

			admin2.setAdminContacts(EntityToDtoMapping.contactsEntityToContactsReq(admin.getAdminContacts()));
			admin2.setAdminName(admin.getAdminName());
			admin2.setIsApproved(admin.getIsApproved());
			adminList.add(admin2);
		}
		return adminList;
	}
	
	public static AddressResponse AddressToAddressResponse(Address address) {
		AddressResponse addressResponse = new AddressResponse();
		addressResponse.setCity(address.getCity());
		addressResponse.setStreet(address.getStreet());
		addressResponse.setState(address.getState());
		addressResponse.setCountry(address.getCountry());
		addressResponse.setZipCode(address.getZipCode());
		return addressResponse;
		
	}
	
	
	public static CustomerResponse customerToCustomerResponse(Customer customer) {
		CustomerResponse customerResponse = new CustomerResponse();
		customerResponse.setCustomerName(customer.getCustomerName());
		customerResponse.setEmailId(customer.getEmailId());
		customerResponse.setMobileNumber(customer.getMobileNumber());
		return customerResponse;
	}
	
	
	public static AdminResponse  adminToAdminResponse(Admin admin) {
		AdminResponse adminResponse = new AdminResponse();
		adminResponse.setAdminName(admin.getAdminName());
		
		adminResponse.setAdminContacts(contactsEntityToContactsReq(admin.getAdminContacts()));
        adminResponse.setAdminAddresses(addressEntityToAddressReq(admin.getAdminAddresses()));
        adminResponse.setIsApproved(admin.getIsApproved());
		
        return adminResponse;
	}
	
}

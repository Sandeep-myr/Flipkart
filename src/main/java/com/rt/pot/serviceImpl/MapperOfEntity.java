package com.rt.pot.serviceImpl;

import com.rt.pot.model.Address;
import com.rt.pot.model.Contacts;
import com.rt.pot.modelReq.AddressReq;
import com.rt.pot.modelReq.ContactsReq;

public class MapperOfEntity {

	public static Contacts contactReqToContact(ContactsReq contactReq, Contacts contacts) {
		contacts.setEmailId(contactReq.getEmailId());
		contacts.setMobileNumber(contactReq.getMobileNumber());
		return contacts;

	}

	public static Address AddressReqToAddress(AddressReq addrerssReq, Address address) {
		address.setStreet(addrerssReq.getStreet());
		address.setCity(addrerssReq.getCity());
		address.setState(addrerssReq.getState());
		address.setCountry(addrerssReq.getCountry());
		address.setZipCode(addrerssReq.getZipCode());

		return address;

	}

}

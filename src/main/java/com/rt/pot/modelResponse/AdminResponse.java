package com.rt.pot.modelResponse;

import java.util.List;

import com.rt.pot.modelReq.AddressReq;
import com.rt.pot.modelReq.ContactsReq;

import lombok.Data;

@Data
public class AdminResponse {

	private String adminName;

	private List<AddressReq> adminAddresses;

	private List<ContactsReq> adminContacts;

	private String isApproved;

}

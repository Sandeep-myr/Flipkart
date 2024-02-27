package com.rt.pot.modelReq;

import java.util.Set;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class AdminReq {

	@Length(max = 50)
	private String adminName;

	@Valid
	private Set<AddressReq> adminAddresses;

	@Valid
	private Set<ContactsReq> adminContacts;

}

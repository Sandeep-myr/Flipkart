package com.rt.pot.modelReq;

import lombok.Data;

@Data
public class AddressReq {

	private String city;

	private String street;
	private String state;

	private String country;
	private Integer zipCode;

}

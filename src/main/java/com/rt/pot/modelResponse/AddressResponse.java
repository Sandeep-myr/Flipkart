package com.rt.pot.modelResponse;

import lombok.Data;

@Data
public class AddressResponse {

	private String city;

	private String street;
	private String state;

	private String country;
	private Integer zipCode;

}

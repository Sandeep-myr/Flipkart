package com.rt.pot.modelResponse;

import lombok.Data;

@Data
public class ManagerResponse {

	private String managerName;

	private String emailId;
	private Long mobileNumber;
	private String city;
	private String street;
	private String state;
	private String country;
	private Integer zipCode;
	private String userName;

}

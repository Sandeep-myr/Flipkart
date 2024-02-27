package com.rt.pot.modelReq;

import lombok.Data;

@Data
public class ManagerReq {

	private String managerName;

	private String emailId;
	private Long mobileNumber;
	private String city;
	private String street;
	private String state;
	private String country;
	private Integer zipCode;
	private String userName;

	private String password;

}

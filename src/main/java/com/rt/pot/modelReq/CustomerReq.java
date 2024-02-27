package com.rt.pot.modelReq;

import lombok.Data;

@Data
public class CustomerReq {

	private String customerName;

	private String emailId;
	private String password;

	private Long mobileNumber;

}

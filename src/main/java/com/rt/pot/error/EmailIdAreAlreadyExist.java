package com.rt.pot.error;

@SuppressWarnings("serial")
public class EmailIdAreAlreadyExist extends RuntimeException {

	public EmailIdAreAlreadyExist(String message) {
		super(message);
	}

}

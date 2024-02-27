package com.rt.pot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "manager")
public class Manager {

	@Id
	@SequenceGenerator(name = "manager_id", sequenceName = "manager_id", allocationSize = 1, initialValue = 1000)
	@GeneratedValue(generator = "manager_id", strategy = GenerationType.SEQUENCE)
	private Integer managerId;

	@Column(name = "manager_name")
	private String managerName;

	@Column(name = "email_id")
	private String emailId;
	@Column(name = "mobile_number")

	private Long mobileNumber;

	@Column(name = "city")

	private String city;
	@Column(name = "street")

	private String street;
	@Column(name = "state")

	private String state;
	@Column(name = "country")
	private String country;
	@Column(name = "zip_code")
	private Integer zipCode;

	@Column(name = "user_name")

	private String userName;
	@Column(name = "password")
	private String password;

}

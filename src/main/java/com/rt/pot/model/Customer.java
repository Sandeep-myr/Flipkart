package com.rt.pot.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

@Table(name = "customer_details")
public class Customer {

	@Id
	@SequenceGenerator(name = "customer_id", sequenceName = "customer_id", allocationSize = 1, initialValue = 1000)
	@GeneratedValue(generator = "customer_id", strategy = GenerationType.SEQUENCE)
	private Integer customerId;

	@Column(name = "customer_name", nullable = false)
	private String customerName;

	@Column(name = "email_id", nullable = false, unique = true)
	@Email
	private String emailId;

	@Column(name = "password", nullable = false, length = 32)
	private String password;

	@Column(name = "mobile_number", length = 12, unique = true)
	private Long mobileNumber;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<OrderRecived> orderRecived;
}

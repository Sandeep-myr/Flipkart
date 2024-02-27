package com.rt.pot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "contact_details")

public class Contacts {

	@Id
	@SequenceGenerator(name = "contact_id", sequenceName = "contact_id", allocationSize = 1)
	@GeneratedValue(generator = "contact_id", strategy = GenerationType.SEQUENCE)
	private Integer contact_id;

	@Column(name = "mobile_number", nullable = false)

	private Long mobileNumber;

	@Column(name = "email_id", nullable = false)

	private String emailId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "admin_id", referencedColumnName = "adminId")
	private Admin adminData;

}

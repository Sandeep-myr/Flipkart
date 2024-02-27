package com.rt.pot.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data

@NoArgsConstructor
@Table(name = "address_details")

public class Address {

	@Id
	@SequenceGenerator(name = "address_id", sequenceName = "address_id", allocationSize = 1)
	@GeneratedValue(generator = "address_id", strategy = GenerationType.SEQUENCE)
	private Integer addressId;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "street", nullable = false)
	private String street;

	@Column(name = "state", nullable = false)
	private String state;

	@Column(name = "country", nullable = false)
	private String country;

	@Column(name = "zip_code", nullable = false)
	private Integer zipCode;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "admin_id", referencedColumnName = "adminId")
	private Admin adminData;

	@OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
	private OrderRecived orderRecived;

}

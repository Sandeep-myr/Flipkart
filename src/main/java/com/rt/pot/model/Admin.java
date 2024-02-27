package com.rt.pot.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "admin_details")
public class Admin {

	@Id
	@SequenceGenerator(name = "admin_id", sequenceName = "admin_id", allocationSize = 1)
	@GeneratedValue(generator = "admin_id", strategy = GenerationType.SEQUENCE) // 1
	private Integer adminId;

	@NotNull
	@Column(name = "admin_name")
	private String adminName;

	@OneToMany(mappedBy = "adminData", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Address> adminAddresses;

	@OneToMany(mappedBy = "adminData", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Contacts> adminContacts;

	@OneToMany(mappedBy = "adminData", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Product> products;

	@Column(name = "active", columnDefinition = "varchar(20) default 'No'", insertable = false)
	private String isApproved;

	@OneToOne(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)

	private LoginDetails loginDetails;

	@OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
	private List<OrderDetails> orderDetails;

}

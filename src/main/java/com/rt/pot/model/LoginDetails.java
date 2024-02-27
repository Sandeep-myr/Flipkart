package com.rt.pot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "login_details")
public class LoginDetails {

	@Id
	@SequenceGenerator(name = "login_id", sequenceName = "login_id", allocationSize = 1)
	@GeneratedValue(generator = "login_id", strategy = GenerationType.SEQUENCE)
	private Integer loginId;

	private String userEmailId;

	private String password;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "admin_id", referencedColumnName = "adminId")
	private Admin admin;

}

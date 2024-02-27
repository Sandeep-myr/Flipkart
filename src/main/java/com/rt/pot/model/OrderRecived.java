package com.rt.pot.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order_recieved")
public class OrderRecived {

	@Id
	@SequenceGenerator(name = "order_recieved_id_seq", sequenceName = "order_recieved_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "order_recieved_id_seq", strategy = GenerationType.SEQUENCE)
	@Column(name = "order_recived_id", unique = true, nullable = false)
	private Integer orderRecivedId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", referencedColumnName = "customerId")
	private Customer customer;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "address_id", referencedColumnName = "addressId")
	private Address address;

	@Column(name = "order_date", nullable = false)
	private LocalDateTime dateTime;

	@Column(name = "total_price", nullable = false)
	private Double totalPrice;

	@Column(name = "total_saving", nullable = false)
	private Double totalSaving;

	@Column(name = "amount_pay", nullable = false)
	private Double amountPay;

	@OneToMany(mappedBy = "orderRecived", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderDetails> orderDetails;

}

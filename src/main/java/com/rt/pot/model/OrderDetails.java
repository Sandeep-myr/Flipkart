package com.rt.pot.model;

import com.rt.pot.orderEnum.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//
@Entity
@Getter
@Setter

@Table(name = "order_details")
public class OrderDetails {

	@Id
	@SequenceGenerator(name = "order_id", sequenceName = "order_id", initialValue = 10000, allocationSize = 1)
	@GeneratedValue(generator = "order_id", strategy = GenerationType.SEQUENCE)
	private Long orderId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", referencedColumnName = "productId")
	private Product product;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "admin_id", referencedColumnName = "adminId")
	private Admin admin;

	@Column(name = "qty", nullable = false)
	private Integer productQty;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_recieve_id", referencedColumnName = "order_recived_id")
	private OrderRecived orderRecived;

//	columnDefinition = "varchar(20) default 'Order Recieved'", insertable = false
	@Enumerated(EnumType.STRING)
	@Column(name = "order_status")
	private OrderStatus orderStatus;

	@OneToOne(mappedBy = "orderDetails", cascade = CascadeType.ALL)
	private ProductReview productReview;

}

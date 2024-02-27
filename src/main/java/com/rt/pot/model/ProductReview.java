package com.rt.pot.model;

import jakarta.persistence.Column;
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
@Table(name = "product_review")
public class ProductReview {

	@Id
	@SequenceGenerator(name = "product_review_id", sequenceName = "product_review_id", allocationSize = 1, initialValue = 1000)
	@GeneratedValue(generator = "product_review_id", strategy = GenerationType.SEQUENCE)
	private Integer productReviewId;

	@Column(name = "star_rating")
	private Integer starRating;

	@Column(name = "review_headline")
	private String reviewHeadline;

	@Column(name = "comment")
	private String comment;

	@Column(name = "customer_public_name")
	private String customerPublicName;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id", referencedColumnName = "orderId")
	private OrderDetails orderDetails;

}

package com.rt.pot.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

@Table(name = "product_details")
public class Product {

	@Id
	@SequenceGenerator(name = "product_id", sequenceName = "product_id", allocationSize = 1, initialValue = 100)
	@GeneratedValue(generator = "product_id", strategy = GenerationType.SEQUENCE)
	private Integer productId;

	private String productName;

	private String brandName;

	private Integer productQty;

	private Double productPrice;

	private Double discountPercentage;

	private String description;

	private String imageUrl;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", referencedColumnName = "categoryId")
	private Category category;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "admin_id", referencedColumnName = "adminId")
	private Admin adminData;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<OrderDetails> orderDetails;
}

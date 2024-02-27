package com.rt.pot.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString

@Table(name = "category_details")
public class Category {

	@Id
	@SequenceGenerator(name = "category_id", sequenceName = "category_id", allocationSize = 1)
	@GeneratedValue(generator = "category_id", strategy = GenerationType.SEQUENCE)
	private Integer categoryId;

	private String categoryName;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Product> productList;

}

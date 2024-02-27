package com.rt.pot.modelReq;

import java.util.Set;

import lombok.Data;

@Data
public class CateogaryReq {

	private String categoryName;

	private Set<ProductReq> productList;

}

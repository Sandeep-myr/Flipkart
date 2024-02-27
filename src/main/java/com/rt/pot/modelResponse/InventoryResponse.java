package com.rt.pot.modelResponse;

import java.util.List;

import lombok.Data;

@Data
public class InventoryResponse {

	private AdminResponse adminResponse;

	private List<ProductResponse> productResponses;

}

package com.rt.pot.modelReq;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderRequest {

	@Min(value = 0, message = "Atleast Add one Item in cart")
	@Positive(message = "greater than zero value accepted")
	private Integer productQty;

}

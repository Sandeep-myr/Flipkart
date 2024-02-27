package com.rt.pot.modelResponse;

import java.util.Map;

import lombok.Data;

@Data
public class TurnOver {

	private Map<Integer,Double > yearWiseSellsMap;;
	
	 private Double totalSells;
}

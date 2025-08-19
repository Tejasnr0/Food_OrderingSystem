package com.Tej.Request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class IngredientCategoryReq {
	private String name;
	private Long restaturantId;
}

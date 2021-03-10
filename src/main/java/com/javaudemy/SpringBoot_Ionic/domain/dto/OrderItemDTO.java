package com.javaudemy.SpringBoot_Ionic.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class OrderItemDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message="Preenchimento obrigatório")
	private Integer quantity;
	
	@NotNull(message="Preenchimento obrigatório")
	private Integer productId;

	public OrderItemDTO() {
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
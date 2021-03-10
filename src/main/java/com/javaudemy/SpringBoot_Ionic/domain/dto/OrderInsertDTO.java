package com.javaudemy.SpringBoot_Ionic.domain.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.javaudemy.SpringBoot_Ionic.domain.Payment;

public class OrderInsertDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message="Preenchimento obrigatório")
	private Integer clientId;
	
	@NotNull(message="Preenchimento obrigatório")
	private Integer deliveryAddressId;
	
	@NotNull(message="Preenchimento obrigatório")
	private Payment payment;
	
	@NotEmpty(message="Preenchimento obrigatório de pelo menos 1 produto")
	@Valid
	private Set<OrderItemDTO> items = new HashSet<>();
	
	public OrderInsertDTO() {
	}
	
	public Integer getClientId() {
		return clientId;
	}


	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}


	public Integer getDeliveryAddressId() {
		return deliveryAddressId;
	}


	public void setDeliveryAddressId(Integer deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	}


	public Payment getPayment() {
		return payment;
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	public Set<OrderItemDTO> getItems() {
		return items;
	}
}

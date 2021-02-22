package com.javaudemy.SpringBoot_Ionic.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.javaudemy.SpringBoot_Ionic.domain.enums.PaymentState;

@Entity
@Table(name = "tb_card_payment")
public class CardPayment extends Payment{

	private static final long serialVersionUID = 1L;
	
	private Integer installments;
	
	public CardPayment() {
	}
	
	public CardPayment(Integer id, PaymentState state, Order order, Integer installments) {
		super(id, state, order);
		this.installments = installments;
	}

	public Integer getInstallments() {
		return installments;
	}

	public void setInstallments(Integer installments) {
		this.installments = installments;
	}
}
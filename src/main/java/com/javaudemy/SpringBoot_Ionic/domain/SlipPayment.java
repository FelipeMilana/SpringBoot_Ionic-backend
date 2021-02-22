package com.javaudemy.SpringBoot_Ionic.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.javaudemy.SpringBoot_Ionic.domain.enums.PaymentState;

@Entity
@Table(name = "tb_slip_payment")
public class SlipPayment extends Payment{

	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;
	@Temporal(TemporalType.DATE)
	private Date paymentDate;
	
	public SlipPayment() {
	}
	
	public SlipPayment(Integer id, PaymentState state, Order order, Date endDate, Date paymentDate) {
		super(id, state, order);
		this.endDate = endDate;
		this.paymentDate = paymentDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
}
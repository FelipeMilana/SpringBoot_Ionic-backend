package com.javaudemy.SpringBoot_Ionic.services;

import java.util.Date;

import org.springframework.mail.SimpleMailMessage;

import com.javaudemy.SpringBoot_Ionic.domain.Order;

public abstract class AbstractEmailService implements EmailService {
	
	@Override
	public void sendOrderConfirmationEmail(Order order) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromOrder(order);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromOrder(Order order) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(order.getClient().getEmail());
		sm.setFrom("mockTeste@gmail.com");
		sm.setSubject("Pedido confirmado! Código: " + order.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(order.toString());
		return sm;
	}
}

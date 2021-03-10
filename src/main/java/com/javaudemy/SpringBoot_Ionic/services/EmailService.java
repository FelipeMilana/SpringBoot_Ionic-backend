package com.javaudemy.SpringBoot_Ionic.services;

import org.springframework.mail.SimpleMailMessage;

import com.javaudemy.SpringBoot_Ionic.domain.Order;

public interface EmailService {

	void sendOrderConfirmationEmail(Order order);
	
	void sendEmail(SimpleMailMessage mail);
}

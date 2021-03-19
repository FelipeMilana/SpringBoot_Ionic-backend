package com.javaudemy.SpringBoot_Ionic.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.javaudemy.SpringBoot_Ionic.domain.Client;
import com.javaudemy.SpringBoot_Ionic.domain.Order;

public abstract class AbstractEmailService implements EmailService {
	
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	protected String htmlFromOrderTemplate(Order order) {
		Context context = new Context();
		context.setVariable("order", order);
		return templateEngine.process("email/orderConfirmation", context);
		
	}
	
	@Override
	public void sendOrderConfirmationEmailHtml(Order order) {
		try {
			MimeMessage mm = prepareMimeMessageFromOrder(order);
			sendHtmlEmail(mm);
		}
		catch(MessagingException e) {
			sendOrderConfirmationEmail(order);
		}
	}
	
	protected MimeMessage prepareMimeMessageFromOrder(Order order) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(order.getClient().getEmail());
		mmh.setFrom("mockTeste@gmail.com");
		mmh.setSubject("Pedido Confirmado! Código: "+ order.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromOrderTemplate(order), true);
		return mimeMessage;
		
	}
	
	@Override
	public void sendNewPasswordEmail(Client client, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(client, newPass);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Client client, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(client.getEmail());
		sm.setFrom("mockTeste@gmail.com");
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: "+ newPass);
		return sm;
	}
}

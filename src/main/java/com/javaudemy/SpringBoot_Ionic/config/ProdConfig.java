package com.javaudemy.SpringBoot_Ionic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.javaudemy.SpringBoot_Ionic.services.EmailService;
import com.javaudemy.SpringBoot_Ionic.services.SmtpEmailService;


@Configuration
@Profile("prod")
public class ProdConfig  {
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}

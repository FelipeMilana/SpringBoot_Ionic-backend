package com.javaudemy.SpringBoot_Ionic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaudemy.SpringBoot_Ionic.domain.CardPayment;
import com.javaudemy.SpringBoot_Ionic.domain.SlipPayment;

@Configuration
public class JacksonConfig {
	
	// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-
	//without-hinting-the-pare
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(CardPayment.class);
				objectMapper.registerSubtypes(SlipPayment.class);
				super.configure(objectMapper);
			};
		};
		return builder;
	}
	

}

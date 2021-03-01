package com.javaudemy.SpringBoot_Ionic.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.javaudemy.SpringBoot_Ionic.services.validations.UpdateClient;

@UpdateClient
public class ClientUpdateDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Size(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
	private String name;
	
	@Email(message="Email inválido")
	private String email;
	
	private String telephone1;
	private String telephone2;
	private String telephone3;
	
	public ClientUpdateDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone1() {
		return telephone1;
	}

	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}

	public String getTelephone2() {
		return telephone2;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}

	public String getTelephone3() {
		return telephone3;
	}

	public void setTelephone3(String telephone3) {
		this.telephone3 = telephone3;
	}
}
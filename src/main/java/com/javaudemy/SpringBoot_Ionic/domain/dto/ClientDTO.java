package com.javaudemy.SpringBoot_Ionic.domain.dto;

import java.io.Serializable;

import com.javaudemy.SpringBoot_Ionic.domain.Client;

public class ClientDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String email;
	private String imgUrl;
	
	public ClientDTO() {
	}
	
	public ClientDTO(Client obj) {
		this.id = obj.getId();
		this.name = obj.getName();
		this.email = obj.getEmail();
		this.imgUrl = obj.getImageURL();
	}
	
	public ClientDTO(Integer id, String name, String email, String imgUrl) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.imgUrl = imgUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	public String getImgUrl() {
		return imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
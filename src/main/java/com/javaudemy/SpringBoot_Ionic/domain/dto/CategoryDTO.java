package com.javaudemy.SpringBoot_Ionic.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.javaudemy.SpringBoot_Ionic.domain.Category;

public class CategoryDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Size(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
	private String name;
	
	public CategoryDTO()	{
	}
	
	public CategoryDTO(Category obj) {
		this.id  = obj.getId();
		this.name = obj.getName();
	}
	
	public CategoryDTO(Integer id, String name) {
		this.id = id;
		this.name = name;
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
}

package com.javaudemy.SpringBoot_Ionic.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ProductInsertDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message="Preenchimento obrigatório")
	@Size(min=2, max=120, message="O tamanho deve ser entre 2 e 120 caracteres")
	private String name;
	
	@NotNull(message="Preenchimento obrigatório")
	@Positive(message="preço inválido")
	private Double price;
	
	@NotEmpty(message="Preenchimento obrigatório de pelo menos 1 categoria")
	private List<CategoryDTO> categoryName = new ArrayList<>();
	
	public ProductInsertDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<CategoryDTO> getCategoryName() {
		return categoryName;
	}
}
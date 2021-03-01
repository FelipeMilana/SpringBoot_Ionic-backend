package com.javaudemy.SpringBoot_Ionic.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Double price;
	private List<CategoryDTO> insertCategoryName = new ArrayList<>();
	private List<CategoryDTO> removeCategoryName = new ArrayList<>();
	
	public ProductUpdateDTO() {
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

	public List<CategoryDTO> getInsertCategoryName() {
		return insertCategoryName;
	}
	
	public List<CategoryDTO> getRemoveCategoryName() {
		return removeCategoryName;
	}
}
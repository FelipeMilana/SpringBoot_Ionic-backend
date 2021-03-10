package com.javaudemy.SpringBoot_Ionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.javaudemy.SpringBoot_Ionic.domain.Category;
import com.javaudemy.SpringBoot_Ionic.domain.Product;
import com.javaudemy.SpringBoot_Ionic.domain.dto.CategoryDTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.ProductInsertDTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.ProductUpdateDTO;
import com.javaudemy.SpringBoot_Ionic.repositories.CategoryRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.ProductRepository;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.DataIntegrityException;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	@Autowired
	private CategoryRepository catRepository;
	
	
	public List<Product> findAll() {
		return repository.findAll();
	}

	public Product findById(Integer id) {
		Optional<Product> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Product.class.getName()));
	}

	public Product insert(Product obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Product update(Integer id, Product obj) {
		Product oldObj = findById(id);
		obj = updating(oldObj, obj);
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um produto que está presente em um item de pedido");
		}
	}
	
	public Page<Product> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Category> categories = catRepository.findAllById(ids);
		return repository.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);
	}

	public Product fromDTO(ProductInsertDTO objDTO) {
		Product prod = new Product(null, objDTO.getName(), objDTO.getPrice());

		for (CategoryDTO cat : objDTO.getCategoryName()) {
			Category category = catRepository.findByName(cat.getName());
			if (category != null) {
				category.getProducts().add(prod);
				prod.getCategories().add(category);
			}
		}
		
		return prod;
	}
	
	public Product fromDTO(ProductUpdateDTO objDTO) {
		Product prod = new Product(null, objDTO.getName(), objDTO.getPrice());

		for (CategoryDTO cat : objDTO.getInsertCategoryName()) {
			Category category = catRepository.findByName(cat.getName());
	
			if (category != null) {
				prod.getCategories().add(category);
				category.getProducts().add(prod);
			}
		}
		
		for (CategoryDTO cat : objDTO.getRemoveCategoryName()) {
			Category category = catRepository.findByName(cat.getName());
			
			if (category != null) {
				prod.getCategories().add(category);
				category.getProducts().remove(prod);
			}
		}
		return prod;
	}

	private Product updating(Product oldObj, Product obj) {
		if(obj.getName() != null) {
			oldObj.setName(obj.getName());
		}
		if(obj.getPrice() != null) {
			oldObj.setPrice(obj.getPrice());
		}
		
		for(Category cat : obj.getCategories()) {
			if(oldObj.getCategories().contains(cat)) {
				oldObj.getCategories().remove(cat);
			}
			else {
				oldObj.getCategories().add(cat);
			}
		}
		
		return oldObj;
	}
}
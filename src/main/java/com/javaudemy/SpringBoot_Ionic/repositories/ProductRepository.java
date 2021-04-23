package com.javaudemy.SpringBoot_Ionic.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.javaudemy.SpringBoot_Ionic.domain.Category;
import com.javaudemy.SpringBoot_Ionic.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

//	@Transactional(readOnly=true)
//	@Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cat WHERE obj.name LIKE %:name% AND cat IN :categories")
//	Page<Product> search(@Param("name") String name, @Param("categories") List<Category> categories, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	Page<Product> findDistinctByNameContainingIgnoreCaseAndCategoriesIn(String name, List<Category> categories, Pageable pageRequest);
}

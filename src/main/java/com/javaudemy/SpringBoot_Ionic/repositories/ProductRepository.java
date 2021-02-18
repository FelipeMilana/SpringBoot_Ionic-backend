package com.javaudemy.SpringBoot_Ionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaudemy.SpringBoot_Ionic.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}

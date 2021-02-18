package com.javaudemy.SpringBoot_Ionic.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.javaudemy.SpringBoot_Ionic.domain.Category;
import com.javaudemy.SpringBoot_Ionic.repositories.CategoryRepository;

@Configuration
@Profile("test")
public class Instantiation implements CommandLineRunner {

	@Autowired
	private CategoryRepository catRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		catRepository.deleteAll();
		
		Category cat1 = new Category(null, "Informatica");
		Category cat2 = new Category(null, "Escritorio");
		
		catRepository.saveAll(Arrays.asList(cat1, cat2));
	}

}

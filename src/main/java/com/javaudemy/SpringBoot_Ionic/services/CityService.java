package com.javaudemy.SpringBoot_Ionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaudemy.SpringBoot_Ionic.domain.City;
import com.javaudemy.SpringBoot_Ionic.repositories.CityRepository;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.ObjectNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;

	public List<City> findAll() {
		return repository.findAll();
	}

	public City findById(Integer id) {
		Optional<City> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + City.class.getName()));
	}
}
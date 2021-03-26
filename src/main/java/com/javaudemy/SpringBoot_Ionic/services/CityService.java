package com.javaudemy.SpringBoot_Ionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.javaudemy.SpringBoot_Ionic.domain.City;
import com.javaudemy.SpringBoot_Ionic.domain.State;
import com.javaudemy.SpringBoot_Ionic.domain.dto.CityInsertDTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.CityUpdateDTO;
import com.javaudemy.SpringBoot_Ionic.repositories.CityRepository;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.DataIntegrityException;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.ObjectNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;
	@Autowired
	private StateService stateService;

	public List<City> findAll() {
		return repository.findAll();
	}

	public List<City> findByStateIdOrderByName(Integer stateId) {
		List<City> cities = repository.findByStateIdOrderByName(stateId);
		
		if(cities.isEmpty()) {
			throw new ObjectNotFoundException("Não há cidades nesse estado");
		}
		return cities;
	}
	
	public City findById(Integer id) {
		Optional<City> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + City.class.getName()));
	}
	
	public City insert(City obj) {
		return repository.save(obj);
	}
	
	public City update(Integer id, City obj) {
		City oldObj = findById(id);
		obj = updating(oldObj, obj);
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir esta cidade");
		}
	}
	
	public City fromDTO(CityInsertDTO objDTO) {
		State state = stateService.findById(objDTO.getStateId());
		City city = new City(null, objDTO.getName(), state);
		
		state.getCities().add(city);
		stateService.insert(state);
		return city;
	}
	
	public City fromDTO(CityUpdateDTO objDTO) {
		return new City(null, objDTO.getName(), null);
	}
	
	private City updating(City oldObj, City obj) {
		oldObj.setName(obj.getName());
		return oldObj;
	}
}
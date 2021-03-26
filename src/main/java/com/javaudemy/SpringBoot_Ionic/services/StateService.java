package com.javaudemy.SpringBoot_Ionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.javaudemy.SpringBoot_Ionic.domain.State;
import com.javaudemy.SpringBoot_Ionic.domain.dto.StateDTO;
import com.javaudemy.SpringBoot_Ionic.repositories.StateRepository;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.DataIntegrityException;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.ObjectNotFoundException;

@Service
public class StateService {

	@Autowired
	private StateRepository repository;
	
	public List<State> findAll() {
		return repository.findAll();
	}
	
	public List<State> findAllByOrderByName() {
		return repository.findAllByOrderByName();
	}
	
	public State findById(Integer id) {
		Optional<State> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + State.class.getName()));
	}
	
	public State insert(State obj) {
		return repository.save(obj);
	}
	
	public State update(Integer id, State obj) {
		State oldObj = findById(id);
		obj = updating(oldObj, obj);
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);
		
		try {
			repository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um estado que possui cidades");
		}
	}
	
	public State fromDTO(StateDTO objDTO) {
		State state = new State(null, objDTO.getName());
		return state;
	}
	
	private State updating(State oldObj, State obj) {
		oldObj.setName(obj.getName());
		return oldObj;
	}

}

package com.javaudemy.SpringBoot_Ionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.javaudemy.SpringBoot_Ionic.domain.Address;
import com.javaudemy.SpringBoot_Ionic.repositories.AddressRepository;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.DataIntegrityException;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.ObjectNotFoundException;

@Service
public class AddressService {

	@Autowired
	private AddressRepository repository;
	
	public List<Address> findAll()	{
		return repository.findAll();
	}
	
	public Address findById(Integer id) {
		Optional<Address> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Address.class.getName()));
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um endereço que esteja sendo utilizado em um pedido");
		}
	}
}
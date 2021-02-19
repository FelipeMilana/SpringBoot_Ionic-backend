package com.javaudemy.SpringBoot_Ionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaudemy.SpringBoot_Ionic.domain.Client;
import com.javaudemy.SpringBoot_Ionic.repositories.ClientRepository;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	public ClientRepository repository;
	
	public List<Client> findAll()	{
		return repository.findAll();
	}
	
	public Client findById(Integer id) {
		Optional<Client> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}
}
package com.javaudemy.SpringBoot_Ionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.javaudemy.SpringBoot_Ionic.domain.Adress;
import com.javaudemy.SpringBoot_Ionic.domain.City;
import com.javaudemy.SpringBoot_Ionic.domain.Client;
import com.javaudemy.SpringBoot_Ionic.domain.dto.Client2DTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.ClientDTO;
import com.javaudemy.SpringBoot_Ionic.domain.enums.ClientType;
import com.javaudemy.SpringBoot_Ionic.repositories.CityRepository;
import com.javaudemy.SpringBoot_Ionic.repositories.ClientRepository;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.DataIntegrityException;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	public ClientRepository repository;
	@Autowired
	public CityRepository cityRepository;

	public List<Client> findAll() {
		return repository.findAll();
	}

	public Client findById(Integer id) {
		Optional<Client> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}

	public Client insert(Client obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Client update(Integer id, Client obj) {
		Client oldObj = findById(id);
		obj = updating(oldObj, obj);
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}

	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Client fromDTO(ClientDTO objDTO) {
		return new Client(objDTO.getId(), objDTO.getName(), objDTO.getEmail(), null, null);
	}

	public Client fromDTO(Client2DTO objDTO) {
		Client cli = new Client(null, objDTO.getName(), objDTO.getEmail(), objDTO.getCpfOrCnpj(),
				ClientType.toStringEnum(objDTO.getType()));
		
		Optional<City> city = cityRepository.findById(objDTO.getCityId());
		
		Adress adress = new Adress(null, objDTO.getStreet(), objDTO.getNumber(), objDTO.getComplement(),
				objDTO.getDistrict(), objDTO.getCep(), cli, city.orElseThrow(
						() -> new ObjectNotFoundException("Cidade não encontrada! Id: " + objDTO.getCityId())));
		
		cli.getAdresses().add(adress);
		cli.getTelephones().add(objDTO.getTelephone1());

		if (objDTO.getTelephone2() != null) {
			cli.getTelephones().add(objDTO.getTelephone2());
		}
		if (objDTO.getTelephone3() != null) {
			cli.getTelephones().add(objDTO.getTelephone3());
		}
		return cli;
	}

	private Client updating(Client oldObj, Client obj) {
		oldObj.setName(obj.getName());
		oldObj.setEmail(obj.getEmail());
		return oldObj;
	}
}
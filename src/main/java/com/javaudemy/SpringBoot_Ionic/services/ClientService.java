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
import com.javaudemy.SpringBoot_Ionic.domain.dto.AdressInsertDTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.AdressUpdateDTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.ClientInsertDTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.ClientUpdateDTO;
import com.javaudemy.SpringBoot_Ionic.domain.enums.ClientType;
import com.javaudemy.SpringBoot_Ionic.repositories.AdressRepository;
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
	@Autowired
	public AdressRepository adressRepository;

	public List<Client> findAll() {
		return repository.findAll();
	}

	public Client findById(Integer id) {
		Optional<Client> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}

	public Client insert(Client obj) {
		return repository.save(obj);
	}

	public Client update(Integer id, Client obj) {
		Client oldObj = findById(id);
		obj = updating(oldObj, obj);
		return repository.save(obj);
	}
	
	public Client updateAdress(Integer id, Adress obj) {
		Client oldObj = findById(id);
		oldObj = updatingAdress(oldObj, obj);
		return repository.save(oldObj);	
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}

	public void deleteAdress(Integer id, Integer adressId) {
		Client cli = findById(id);
		Optional<Adress> adress = adressRepository.findById(adressId);
		
		if(cli.getAdresses().contains(adress.get())) {
			cli.getAdresses().remove(adress.get());
		}
		adressRepository.deleteById(adressId);
		
	}
	
	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Client fromDTO(ClientUpdateDTO objDTO) {
		Client cli = new Client(null, objDTO.getName(), objDTO.getEmail(), null, null);
		
		cli.getTelephones().add(objDTO.getTelephone1());
		cli.getTelephones().add(objDTO.getTelephone2());
		cli.getTelephones().add(objDTO.getTelephone3());
		
		return cli;
	}

	public Client fromDTO(ClientInsertDTO objDTO) {
		Client cli = new Client(null, objDTO.getName(), objDTO.getEmail(), objDTO.getCpfOrCnpj(),
				ClientType.toStringEnum(objDTO.getType()));
		
		Optional<City> city = cityRepository.findById(objDTO.getCityId());
		
		Adress adress = new Adress(null, objDTO.getStreet(), objDTO.getNumber(), objDTO.getComplement(),
				objDTO.getDistrict(), objDTO.getCep(), cli, city.orElseThrow(
						() -> new ObjectNotFoundException("Cidade não encontrada! Id: " + objDTO.getCityId())));
		
		cli.getAdresses().add(adress);
		
		cli.getTelephones().add(objDTO.getTelephone1());
		
		if(objDTO.getTelephone2() != null) {
			cli.getTelephones().add(objDTO.getTelephone2());
		}
		if(objDTO.getTelephone3() != null) {
			cli.getTelephones().add(objDTO.getTelephone3());
		}
		return cli;
	}
	
	public Client adressFromDTO(AdressInsertDTO objDTO, Integer id) {
		Client cli = findById(id);
		Optional<City> city = cityRepository.findById(objDTO.getCityId());
		
		Adress adress = new Adress(null, objDTO.getStreet(), objDTO.getNumber(), objDTO.getComplement(), objDTO.getDistrict(), 
				objDTO.getCep(), cli,city.orElseThrow(
						() -> new ObjectNotFoundException("Cidade não encontrada! Id: " + objDTO.getCityId())));
		
		cli.getAdresses().add(adress);
		return cli;
	}
	
	public Adress adressFromDTO(AdressUpdateDTO objDTO, Integer adressId) {
		Optional<City> city = cityRepository.findById(objDTO.getCityId());
		
		Adress adress = new Adress(adressId, objDTO.getStreet(), objDTO.getNumber(), objDTO.getComplement(), objDTO.getDistrict(), 
				objDTO.getCep(), null,city.orElseThrow(
						() -> new ObjectNotFoundException("Cidade não encontrada! Id: " + objDTO.getCityId())));
		return adress;
	}
	
	private Client updatingAdress(Client oldObj, Adress obj) {
		for (Adress adress: oldObj.getAdresses()) {
			if(adress.getId().equals(obj.getId())) {
				if(obj.getStreet()!= null) {
					adress.setStreet(obj.getStreet());
				}
				if(obj.getNumber() != null) {
					adress.setNumber(obj.getNumber());
				}
				if(obj.getComplement() != null) {
					adress.setComplement(obj.getComplement());
				}
				if(obj.getDistrict() != null) {
					adress.setDistrict(obj.getDistrict());
				}
				if(obj.getCep() != null) {
					adress.setCep(obj.getCep());
				}
				if(obj.getCity() != null) {
					adress.setCity(obj.getCity());
				}
			}
		}
		return oldObj;
	}

	private Client updating(Client oldObj, Client obj) {
		if(obj.getName() != null) {
			oldObj.setName(obj.getName());
		}
		
		if(obj.getEmail() != null) {
			oldObj.setEmail(obj.getEmail());
		}
			
		for(int i =0; i< oldObj.getTelephones().size(); i++) {
			if(obj.getTelephones().get(i) != null) {
				oldObj.getTelephones().remove(i);
				oldObj.getTelephones().add(i, obj.getTelephones().get(i));
			}	
		}
		
		for(int i = oldObj.getTelephones().size(); i<obj.getTelephones().size(); i++) {
			oldObj.getTelephones().add(i, obj.getTelephones().get(i));
		}	
		return oldObj;
	}
}
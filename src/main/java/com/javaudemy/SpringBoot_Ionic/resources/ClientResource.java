package com.javaudemy.SpringBoot_Ionic.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javaudemy.SpringBoot_Ionic.domain.Address;
import com.javaudemy.SpringBoot_Ionic.domain.Client;
import com.javaudemy.SpringBoot_Ionic.domain.dto.AddressInsertDTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.AddressUpdateDTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.ClientDTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.ClientInsertDTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.ClientUpdateDTO;
import com.javaudemy.SpringBoot_Ionic.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	@Autowired
	private ClientService service;
	
	@GetMapping
	public ResponseEntity<List<ClientDTO>> findAll() {
		List<Client> list = service.findAll();
		List<ClientDTO> listDTO = list.stream().map(obj -> new ClientDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Client> findById(@PathVariable Integer id) {
		Client obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClientDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		Page<Client> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClientDTO> listDTO = list.map(obj -> new ClientDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PostMapping //201
	public ResponseEntity<Void> insert(@Valid @RequestBody ClientInsertDTO objDTO) {
		Client obj = service.fromDTO(objDTO);
		obj =  service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping(value = "/{id}/addAddress") //204
	public ResponseEntity<Void> insertAddress(@Valid @RequestBody AddressInsertDTO objDTO, @PathVariable Integer id) {
		Client obj = service.adressFromDTO(objDTO, id);
		obj =  service.insert(obj);
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping(value = "/{id}") //204
	public ResponseEntity<Void> update(@Valid @RequestBody ClientUpdateDTO objDTO, @PathVariable Integer id){
		Client obj = service.fromDTO(objDTO);
		obj = service.update(id, obj);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}/updateAddress/{adressId}") //204
	public ResponseEntity<Client> updateAddress(@Valid @RequestBody AddressUpdateDTO objDTO, @PathVariable Integer id, @PathVariable Integer adressId) {
		Address adress = service.adressFromDTO(objDTO, adressId);
		service.updateAddress(id, adress);
		return ResponseEntity.noContent().build();
		
	}
	
	@DeleteMapping(value = "/{id}")  //204
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}/deleteAddress/{adressId}") //204
	public ResponseEntity<Void> delete(@PathVariable Integer id, @PathVariable Integer adressId) {
		service.deleteAddress(id, adressId);
		return ResponseEntity.noContent().build();
	}
	
}

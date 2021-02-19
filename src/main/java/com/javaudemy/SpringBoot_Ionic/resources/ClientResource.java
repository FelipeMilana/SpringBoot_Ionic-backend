package com.javaudemy.SpringBoot_Ionic.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaudemy.SpringBoot_Ionic.domain.Client;
import com.javaudemy.SpringBoot_Ionic.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	@Autowired
	private ClientService service;
	
	@GetMapping
	public ResponseEntity<List<Client>> findAll() {
		List<Client> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Client> findById(@PathVariable Integer id) {
		Client obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	
}

package com.javaudemy.SpringBoot_Ionic.services;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaudemy.SpringBoot_Ionic.domain.Address;
import com.javaudemy.SpringBoot_Ionic.domain.City;
import com.javaudemy.SpringBoot_Ionic.domain.Client;
import com.javaudemy.SpringBoot_Ionic.domain.dto.AddressInsertDTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.AddressUpdateDTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.ClientInsertDTO;
import com.javaudemy.SpringBoot_Ionic.domain.dto.ClientUpdateDTO;
import com.javaudemy.SpringBoot_Ionic.domain.enums.ClientType;
import com.javaudemy.SpringBoot_Ionic.domain.enums.Profile;
import com.javaudemy.SpringBoot_Ionic.repositories.ClientRepository;
import com.javaudemy.SpringBoot_Ionic.security.UserSS;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.AuthorizationException;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.DataIntegrityException;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	@Autowired
	private CityService cityService;
	@Autowired
	private AddressService adressService;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private DropboxService dropboxService;
	@Autowired
	private ImageService imgService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;

	public List<Client> findAll() {
		return repository.findAll();
	}

	public Client findById(Integer id) {
		UserSS user = UserService.authenticatedUser();

		if (user == null || (!user.hasRole(Profile.ADMIN) && !id.equals(user.getId()))) {
			throw new AuthorizationException("Acesso negado");
		}

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

	public Client updateAddress(Integer id, Address obj) {
		Client oldObj = findById(id);
		oldObj = updatingAddress(oldObj, obj);
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

	public void deleteAddress(Integer id, Integer adressId) {
		Client cli = findById(id);
		Address adress = adressService.findById(adressId);

		if (cli.getAddresses().contains(adress)) {
			cli.getAddresses().remove(adress);
		}
		adressService.delete(adressId);

	}

	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Client fromDTO(ClientUpdateDTO objDTO) {
		Client cli = new Client(null, objDTO.getName(), objDTO.getEmail(), null, null, objDTO.getPassword());

		cli.getTelephones().add(objDTO.getTelephone1());
		cli.getTelephones().add(objDTO.getTelephone2());
		cli.getTelephones().add(objDTO.getTelephone3());

		return cli;
	}

	public Client fromDTO(ClientInsertDTO objDTO) {
		Client cli = new Client(null, objDTO.getName(), objDTO.getEmail(), objDTO.getCpfOrCnpj(),
				ClientType.toStringEnum(objDTO.getType()), encoder.encode(objDTO.getPassword()));

		City city = cityService.findById(objDTO.getCityId());

		Address adress = new Address(null, objDTO.getStreet(), objDTO.getNumber(), objDTO.getComplement(),
				objDTO.getDistrict(), objDTO.getCep(), cli, city);

		cli.getAddresses().add(adress);

		cli.getTelephones().add(objDTO.getTelephone1());

		if (objDTO.getTelephone2() != null) {
			cli.getTelephones().add(objDTO.getTelephone2());
		}
		if (objDTO.getTelephone3() != null) {
			cli.getTelephones().add(objDTO.getTelephone3());
		}
		return cli;
	}

	public Client adressFromDTO(AddressInsertDTO objDTO, Integer id) {
		Client cli = findById(id);
		City city = cityService.findById(objDTO.getCityId());

		Address adress = new Address(null, objDTO.getStreet(), objDTO.getNumber(), objDTO.getComplement(),
				objDTO.getDistrict(), objDTO.getCep(), cli, city);

		cli.getAddresses().add(adress);
		return cli;
	}

	public Address adressFromDTO(AddressUpdateDTO objDTO, Integer adressId) {
		City city = cityService.findById(objDTO.getCityId());

		Address adress = new Address(adressId, objDTO.getStreet(), objDTO.getNumber(), objDTO.getComplement(),
				objDTO.getDistrict(), objDTO.getCep(), null, city);

		return adress;
	}
	
	public URI uploadProfilePicture(MultipartFile file) {
		UserSS user = UserService.authenticatedUser();
		
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		BufferedImage jpgImage = imgService.getJpgImageFromFile(file);
		String fileName = prefix+ user.getId()+ ".jpg";
		InputStream is = imgService.getInputStream(jpgImage, "jpg");
		URI uri = dropboxService.uploadFile(is, fileName);
		
		Optional<Client> cli = repository.findById(user.getId());
		cli.get().setImageURL(uri.toString());
		repository.save(cli.get());
		
		return uri;
	}

	private Client updatingAddress(Client oldObj, Address obj) {
		for (Address adress : oldObj.getAddresses()) {
			if (adress.getId().equals(obj.getId())) {
				if (obj.getStreet() != null) {
					adress.setStreet(obj.getStreet());
				}
				if (obj.getNumber() != null) {
					adress.setNumber(obj.getNumber());
				}
				if (obj.getComplement() != null) {
					adress.setComplement(obj.getComplement());
				}
				if (obj.getDistrict() != null) {
					adress.setDistrict(obj.getDistrict());
				}
				if (obj.getCep() != null) {
					adress.setCep(obj.getCep());
				}
				if (obj.getCity() != null) {
					adress.setCity(obj.getCity());
				}
			}
		}
		return oldObj;
	}

	private Client updating(Client oldObj, Client obj) {
		if (obj.getName() != null) {
			oldObj.setName(obj.getName());
		}

		if (obj.getEmail() != null) {
			oldObj.setEmail(obj.getEmail());
		}

		for (int i = 0; i < oldObj.getTelephones().size(); i++) {
			if (obj.getTelephones().get(i) != null) {
				oldObj.getTelephones().remove(i);
				oldObj.getTelephones().add(i, obj.getTelephones().get(i));
			}
		}

		for (int i = oldObj.getTelephones().size(); i < obj.getTelephones().size(); i++) {
			oldObj.getTelephones().add(i, obj.getTelephones().get(i));
		}
		
		if(obj.getPassword() != null) {
			oldObj.setPassword(encoder.encode(obj.getPassword()));
		}
		return oldObj;
	}
}
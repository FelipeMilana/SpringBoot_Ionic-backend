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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaudemy.SpringBoot_Ionic.domain.Category;
import com.javaudemy.SpringBoot_Ionic.domain.dto.CategoryDTO;
import com.javaudemy.SpringBoot_Ionic.repositories.CategoryRepository;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.DataIntegrityException;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	@Autowired
	private ImageService imgService;
	@Autowired
	private DropboxService dropboxService;
	
	@Value("${img.prefix.category}")
	private String prefix;
	
	@Value("${img.size}")
	private Integer size;
	
	public List<Category> findAll()	{
		return repository.findAll();
	}
	
	public Category findById(Integer id) {
		Optional<Category> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()));
	}
	
	public Category insert(Category obj) {
		obj.setId(null);
		return repository.save(obj);
	}
	
	public Category update(Integer id, Category obj) {
		Category oldObj = findById(id);
		obj = updating(oldObj, obj);
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Category fromDTO(CategoryDTO objDTO) {
		Category cat = new Category(objDTO.getId(), objDTO.getName());
		
		URI uri = dropboxService.getFile("cat.jpg");
		
		if(uri == null) {
			throw new ObjectNotFoundException("Uri não encontrada");
		}
		
		cat.setImageUrl(uri.toString());
		return cat;
	}
	
	public URI uploadPicture(MultipartFile file, Integer id) {
		
		BufferedImage jpgImage = imgService.getJpgImageFromFile(file);
		
		//image adjustments
		jpgImage = imgService.cropSquare(jpgImage);
		jpgImage = imgService.resize(jpgImage, size);
		
		String fileName = prefix+ id+ ".jpg";
		InputStream is = imgService.getInputStream(jpgImage, "jpg");
		URI uri = dropboxService.uploadFile(is, fileName);
		
		Optional<Category> cat = repository.findById(id);
		cat.get().setImageUrl(uri.toString());
		repository.save(cat.get());
		
		return uri;
	}
	
	private Category updating(Category oldObj, Category obj) {
		oldObj.setName(obj.getName());
		return oldObj;
	}
}
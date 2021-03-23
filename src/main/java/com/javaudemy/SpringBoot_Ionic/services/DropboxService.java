package com.javaudemy.SpringBoot_Ionic.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.javaudemy.SpringBoot_Ionic.services.exceptions.FileException;

@Service
public class DropboxService {

	private Logger LOG = LoggerFactory.getLogger(DropboxService.class);
	
	@Autowired
	private DbxClientV2 dbxClient;
	
	public URI uploadFile(MultipartFile file) {
		try {
			LOG.info("Iniciando upload");
			String fileName = file.getOriginalFilename();
			InputStream inputStream = file.getInputStream();
			FileMetadata metadata = dbxClient.files().uploadBuilder("/" +fileName).uploadAndFinish(inputStream);
			LOG.info("Upload feito");
			String Url = dbxClient.sharing().getFileMetadata(metadata.getId()).getPreviewUrl();
			LOG.info("URL gerada");
			return new URI(Url);
		}
		
		catch (IOException e) {
			throw new FileException("Erro de IO: " + e.getMessage());
		}  
		catch (DbxException e) {
			throw new FileException("Erro de Dbx: " + e.getMessage());
		} 
		catch (URISyntaxException e) {
			throw new FileException("Erro de URISyntax: " + e.getMessage());
		}
	}
}

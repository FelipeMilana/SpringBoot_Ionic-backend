package com.javaudemy.SpringBoot_Ionic.services;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.UploadErrorException;

@Service
public class DropboxService {

	private Logger LOG = LoggerFactory.getLogger(DropboxService.class);
	
	@Autowired
	private DbxClientV2 dbxClient;
	
	public void uploadFile(MultipartFile file, String filePath) {
		try {
			LOG.info("Iniciando upload");
			InputStream inputStream = file.getInputStream();
			dbxClient.files().uploadBuilder(filePath).uploadAndFinish(inputStream);
			LOG.info("Upload feito");
		} 
		
		catch (IOException e) {
			throw new RuntimeException();
		} 
		catch (UploadErrorException e) {
			throw new RuntimeException();
		} 
		catch (DbxException e) {
			throw new RuntimeException();
		}
	}
}

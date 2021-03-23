package com.javaudemy.SpringBoot_Ionic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;


@Configuration
public class DropboxConfig  {
	
	private static final String ACCESS_TOKEN = System.getenv().get("DROPBOX_ACCESS_TOKEN");
	
	@Bean
	public DbxClientV2 dropboxClient() {
		DbxRequestConfig config = new DbxRequestConfig("dropbbox/SpringBoot_Ionic");
		DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
		return client;
	}
}

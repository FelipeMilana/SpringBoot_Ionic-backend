package com.javaudemy.SpringBoot_Ionic.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.javaudemy.SpringBoot_Ionic.security.UserSS;

public class UserService {

	public static UserSS authenticatedUser() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception e) {
			return null;
		}
	}
}

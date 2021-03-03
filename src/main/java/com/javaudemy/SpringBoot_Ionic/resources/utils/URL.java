package com.javaudemy.SpringBoot_Ionic.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.javaudemy.SpringBoot_Ionic.domain.Category;

public class URL {

	public static List<Integer> decodeIntList(String s, List<Category> cats) {
		List<Integer> list = new ArrayList<>();
		
		if(s.equals("All")) {
			list = cats.stream().map(x -> x.getId()).collect(Collectors.toList());
		}
		
		else {
			list = Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
		}
		return list;
	}
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}

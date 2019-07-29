package com.run.service;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;

public interface CreateBookService {

	JSONObject bytext(HttpSession session, JSONObject info, MultipartFile txt) throws Exception;
	JSONObject bysound(HttpSession session, JSONObject info, MultipartFile sound) throws Exception;
	
}

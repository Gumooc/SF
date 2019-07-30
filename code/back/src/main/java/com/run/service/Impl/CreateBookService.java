package com.run.service;

import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpSession;

public interface CreateBookService {

	JSONObject bytext(HttpSession session, JSONObject info, MultipartFile txt) throws Exception;
	JSONObject bysound(HttpSession session,JSONObject info, MultipartFile sound) throws Exception;
}

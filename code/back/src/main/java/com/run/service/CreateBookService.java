package com.run.service;

import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;

public interface CreateBookService {

	JSONObject bytext(JSONObject info, MultipartFile txt);
	JSONObject bysound(JSONObject info, MultipartFile sound);
	
}

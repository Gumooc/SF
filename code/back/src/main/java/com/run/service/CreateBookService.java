package com.run.service;

import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;

public interface CreateBookService {

	JSONObject createbookbytext(int bid, int chapter, MultipartFile txt);
	JSONObject createbookbysound(int bid, int chapter, MultipartFile sound);
	
}

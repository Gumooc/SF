package com.run.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.run.service.CreateBookService;

import net.sf.json.JSONObject;

@Service
public class CreateBookServiceImpl implements CreateBookService {

	@Override
	public JSONObject createbookbytext(int bid, int chapter, MultipartFile txt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject createbookbysound(int bid, int chapter, MultipartFile sound) {
		// TODO Auto-generated method stub
		return null;
	}

}

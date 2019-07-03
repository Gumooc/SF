package com.run.service;

import org.springframework.stereotype.Service;

import com.run.entity.User;

import net.sf.json.JSONObject;

public interface UserService {
	JSONObject logincheck(User user);
	JSONObject useractive(User user);
}

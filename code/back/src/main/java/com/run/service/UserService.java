package com.run.service;

import com.run.entity.User;

import net.sf.json.JSONObject;

public interface UserService {
	JSONObject logincheck(User user);
	JSONObject useractive(int uid);
}

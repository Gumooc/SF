package com.run.service;

import com.run.entity.User;

import net.sf.json.JSONObject;

public interface UserService {
	JSONObject logincheck(User user);
	JSONObject register(User user);
	JSONObject useractive(int uid);
	JSONObject askcollector(int uid);
	JSONObject askhistory(int uid);
	JSONObject askcomment(int uid);
	JSONObject askworks(int uid);
}

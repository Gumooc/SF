package com.run.service.Impl;

import org.springframework.stereotype.Service;

import com.run.entity.User;
import com.run.service.UserService;

import net.sf.json.JSONObject;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public JSONObject logincheck(User user) {
		JSONObject feedback = new JSONObject();
		if (user.getUsername()=="SF") {
			feedback.put("resp", "s");
			feedback.put("body", "");
		}
		return feedback;
	}

	@Override
	public JSONObject useractive(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}

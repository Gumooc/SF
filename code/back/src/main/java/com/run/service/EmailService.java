package com.run.service;

import com.run.entity.User;

import net.sf.json.JSONObject;

public interface EmailService {
	JSONObject sendmail(User user);
}

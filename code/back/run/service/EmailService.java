package com.run.service;

import com.run.entity.Email;

import net.sf.json.JSONObject;

public interface EmailService {
	JSONObject sendmail(Email email);
}

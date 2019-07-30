package com.run.service;

import com.run.entity.UserbookItem;

import net.sf.json.JSONObject;

public interface CollectorService {
	JSONObject delete(UserbookItem ubi);
	JSONObject insert(UserbookItem ubi);
}

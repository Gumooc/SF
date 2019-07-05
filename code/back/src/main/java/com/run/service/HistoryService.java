package com.run.service;

import com.run.entity.UserbookItem;

import net.sf.json.JSONObject;

public interface HistoryService {
	JSONObject clear(int uid);
	JSONObject insert(UserbookItem ubi);
}

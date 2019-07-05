package com.run.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.run.dao.HistoryDao;
import com.run.entity.UserbookItem;
import com.run.service.HistoryService;

import net.sf.json.JSONObject;

@Service
public class HistoryServiceImpl implements HistoryService {
	@Autowired
	private HistoryDao historyMapper;
	
	@Override
	public JSONObject clear(int uid) {
		JSONObject feedback = new JSONObject();
		historyMapper.clear(uid);
		feedback.put("resp", "s");
		feedback.put("body", "");
		return feedback;

	}

	@Override
	public JSONObject insert(UserbookItem ubi) {
		JSONObject feedback = new JSONObject();
		historyMapper.insert(ubi);
		feedback.put("resp", "s");
		feedback.put("body", "");
		return feedback;
	}

}

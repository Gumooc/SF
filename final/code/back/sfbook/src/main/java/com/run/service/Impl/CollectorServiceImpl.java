package com.run.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.run.dao.CollectorDao;
import com.run.entity.UserbookItem;
import com.run.service.CollectorService;

import net.sf.json.JSONObject;

@Service
public class CollectorServiceImpl implements CollectorService {
	@Autowired
	private CollectorDao collectorMapper;
	
	@Override
	public JSONObject delete(UserbookItem ubi) {

		JSONObject feedback = new JSONObject();
		collectorMapper.delete(ubi);
		feedback.put("resp", "s");
		feedback.put("body", "");
		return feedback;
	}

	@Override
	public JSONObject insert(UserbookItem ubi) {
		JSONObject feedback = new JSONObject();
		collectorMapper.insert(ubi);
		feedback.put("resp", "s");
		feedback.put("body", "");
		return feedback;
	}

}

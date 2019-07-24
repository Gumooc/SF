package com.run.service.Impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.run.dao.BookDao;
import com.run.dao.HistoryDao;
import com.run.entity.Book;
import com.run.entity.UserbookItem;
import com.run.service.HistoryService;

import net.sf.json.JSONObject;

@Service
public class HistoryServiceImpl implements HistoryService {
	@Autowired
	private HistoryDao historyMapper;
	@Autowired
	private BookDao bookMapper;
	
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
		Book book = bookMapper.askbookinfo(ubi.getBid());
		//System.out.println(ubi.getUid()+";"+ ubi.getBid()+";"+ ubi.getLst()+";"+ book.getKind());
		historyMapper.insert (ubi.getUid(), ubi.getBid(), book.getKind(), ubi.getLst());
		feedback.put("resp", "s");
		feedback.put("body", "");
		return feedback;
	}

}

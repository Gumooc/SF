package com.run.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.run.dao.UserDao;
import com.run.entity.Book;
import com.run.entity.Collector;
import com.run.entity.Comment;
import com.run.entity.User;
import com.run.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userMapper;
	
	@Override
	public JSONObject logincheck(User user) {
		JSONObject feedback = new JSONObject();
		feedback.put("body", "");
		User ans = userMapper.logincheck(user);
		if (ans!=null) {
			if (ans.getActivation()) {
				feedback.put("resp", "s");
				System.out.println(ans+"ÒÑµÇÂ¼");
			} else {
				feedback.put("resp", "na");
				System.out.println(ans+"Çë¼¤»î");
			}
		} else {
			feedback.put("resp", "f");
			System.out.println("µÇÂ¼Ê§°Ü");
		}
		return feedback;
	}

	@Override
	public JSONObject useractive(int uid) {
		System.out.println("¼¤»î");
		return null;
	}
	
	@Override
	public JSONObject askcollector(int uid) {
		JSONObject feedback = new JSONObject();
		//System.out.println("get:"+uid);
		//List<Integer> aIntegers=userMapper.askcollector(uid);
		Collector bookl = userMapper.askcollector(uid);
		//System.out.println(bookl.getUid());
		//System.out.println(bookl.getBooks());
		//JSONArray booklist = JSONArray.fromObject(bookl.getBooks());
		//System.out.println(booklist);
		feedback.put("body", bookl.getBooks());
		//System.out.println(feedback);
		//System.out.println("got:"+uid);
		return feedback;
	}
	
	@Override
	public JSONObject askhistory(int uid) {
		JSONObject feedback = new JSONObject();
		Collector bookl = userMapper.askhistory(uid);
		feedback.put("body", bookl.getBooks());
		return feedback;
	}
	
	@Override
	public JSONObject askcomment(int uid) {
		JSONObject feedback = new JSONObject();
		List<Comment> commentl = userMapper.askcomment(uid);
		feedback.put("body", commentl);
		return feedback;
	}

	@Override
	public JSONObject askworks(int uid) {
		JSONObject feedback = new JSONObject();
		List<Book> bookl = userMapper.askworks(uid);
		feedback.put("body", bookl);
		return feedback;
	}
}

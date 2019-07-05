package com.run.service.Impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.Base64;
import com.run.dao.UserDao;
import com.run.entity.Book;
import com.run.entity.Collector;
import com.run.entity.Comment;
import com.run.entity.CommentDes;
import com.run.entity.User;
import com.run.entity.UserDetl;
import com.run.service.UserService;

import net.sf.json.JSONObject;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userMapper;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public JSONObject logincheck(User user) {
		JSONObject feedback = new JSONObject();
		feedback.put("body", "");
		User ans = userMapper.logincheck(user);
		if (ans!=null) {
			if (ans.getActivation()) {
				feedback.put("resp", "s");
				System.out.println(ans+"�ѵ�¼");
			} else {
				feedback.put("resp", "na");
				System.out.println(ans+"�뼤��");
			}
		} else {
			feedback.put("resp", "f");
			System.out.println("��¼ʧ��");
		}
		return feedback;
	}

	@Override
	public JSONObject register(User user) {
		JSONObject feedback = new JSONObject();
		if (userMapper.usercheck(user) != null) feedback.put("resp", "f");
	    	else
	        {
	    		System.out.println(user);
	    		if (userMapper.registercheck(user)!=null) {
	    			feedback.put("resp", "ov");
	    		} else {
		    	    int uid = userMapper.getmaxid()+1;
		    	    user.setUid(uid);
		    	    user.setActivation(false);
		    	    user.setAdm(false);
		            userMapper.register(user);
		            feedback.put("resp", "s");
		            JSONObject temp = new JSONObject();
		            temp.put("uid", uid);
		            feedback.put("body", temp);
	    		}
	        }
		return feedback;
	}
	
	@Override
	public JSONObject useractive(int uid) {
		System.out.println("����");
		userMapper.active(uid);
		return null;
	}
	
	@Override
	public JSONObject askcollector(int uid) {
		JSONObject feedback = new JSONObject();
		Collector bookl = userMapper.askcollector(uid);
		feedback.put("body", bookl.getBooks());
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
		for (Comment comment:commentl) {
			Query query = new Query(Criteria.where("id").is(comment.getCid()));
			CommentDes result=mongoTemplate.findOne(query, CommentDes.class, "comment");
			if (result != null) {
				comment.setDes(result.getDes());
			}
		}
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
	
	@Override
	public JSONObject askuser(int uid) {
		JSONObject feedback = new JSONObject();
		User user = userMapper.askuser(uid);
		Query query = new Query(Criteria.where("id").is(uid));
		UserDetl result=mongoTemplate.findOne(query, UserDetl.class, "user");
		if (user == null) {
			feedback.put("resp", "f");
			feedback.put("body", "");
		} else {
			if (result == null) {
				user.setImg("data:image/jpeg;base64,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");
			} else {
				user.setImg(result.getImg());
				user.setBirth(result.getBirth()==null? "0000-00-00":result.getBirth());
			}
			feedback.put("resp", "s");
			feedback.put("body", user);
		}
		return feedback;
	}
	
	@Override
	public JSONObject setImg(int uid, MultipartFile img) {
		JSONObject feedback = new JSONObject();
		String data = "";
		try {
			data = Base64.byteArrayToBase64(img.getBytes());
			UserDetl userDetl = new UserDetl();
			userDetl.setId(uid);
			userDetl.setImg(data);
			mongoTemplate.save(userDetl,"user");
			feedback.put("resp", "s");
			feedback.put("body", "");
		} catch (IOException e) {

			feedback.put("resp", "f");
			feedback.put("body", "");
			e.printStackTrace();
		}
		return feedback;
	}
	
}

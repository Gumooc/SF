package com.run.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.run.dao.CommentDao;
import com.run.entity.Comment;
import com.run.entity.CommentDes;
import com.run.entity.DBindex;
import com.run.service.CommentService;

import net.sf.json.JSONObject;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDao commentMapper;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public JSONObject insert(Comment comment) {
		JSONObject feedback = new JSONObject();
		feedback.put("resp", "f");
		commentMapper.insert(comment);
		CommentDes commentDes = new CommentDes();
		comment.setCid(comment.getCid());
		commentDes.setId(comment.getCid());
		commentDes.setDes(comment.getDes());
		mongoTemplate.save(commentDes,"comment");
		feedback.put("resp", "s");
		return feedback;
	}

	@Override
	public JSONObject delete(Comment comment) {
		JSONObject feedback = new JSONObject();
		feedback.put("resp", "f");
		
		System.out.println(comment);
		Query query = new Query(Criteria.where("_id").is(comment.getCid()));
		mongoTemplate.remove(query, "comment");
		commentMapper.delete(comment);

		feedback.put("resp", "s");
		return feedback;
	}

}

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
		CommentDes commentDes = new CommentDes();
		Query query = new Query(Criteria.where("id").is("commentIndex"));
		DBindex result=mongoTemplate.findOne(query, DBindex.class, "comment");
		if (result == null ) {
			result = new DBindex();
			result.setId("commentIndex");
			result.setValue(10);
		}
		System.out.println(result);
		int index = result.getValue()+1;
		comment.setCid(index);
		commentDes.setId(comment.getCid());
		commentDes.setDes(comment.getDes());
		commentMapper.insert(comment);
		mongoTemplate.save(commentDes,"comment");
		result.setValue(index);
		mongoTemplate.save(result,"comment");
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

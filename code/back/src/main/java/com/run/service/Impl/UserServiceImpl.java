package com.run.service.Impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.Base64;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.run.dao.BookDao;
import com.run.dao.UserDao;
import com.run.entity.Book;
import com.run.entity.BookImg;
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
	@Autowired
	private BookDao bookMapper;
	
	
	@Override
	public JSONObject mdfypassword(int uid, String oldp, String newp) {
		JSONObject feedbody = new JSONObject();
		
		User user = userMapper.askuserallinfo(uid);
		
		if (user.getPassword().equals(oldp)) {
			user.setPassword(newp);
			userMapper.mdfypassword(user);
		}
		return feedbody;
	}
	
	@Override
	public JSONObject logincheck(User user) {
		JSONObject feedback = new JSONObject();
		feedback.put("body", "");
		User ans = userMapper.logincheck(user);
		if (ans!=null) {
			boolean forbidden = userMapper.checkforbidden(ans.getUid());
			if (forbidden) {
				feedback.put("resp", "b");
				System.out.println("µÇÂ¼Ê§°Ü");
			} else {
				if (ans.getActivation()) {
					JSONObject uidjs = new JSONObject();
					uidjs.put("uid", ans.getUid());
					feedback.put("resp", "s");
					feedback.put("body", uidjs);
					System.out.println(ans+"ÒÑµÇÂ¼");
				} else {
					feedback.put("resp", "na");
					System.out.println(ans+"Çë¼¤»î");
				}
			}
		} else {
			feedback.put("resp", "f");
			System.out.println("µÇÂ¼Ê§°Ü");
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
		System.out.println("¼¤»î");
		userMapper.active(uid);
		return null;
	}
	

	@Override
	public JSONObject updateuser(User user) {
		JSONObject feedback = new JSONObject();
		userMapper.updateuser(user);
		feedback.put("resp", "s");
		return feedback;
	}
	
	@Override
	public JSONObject askcollector(int uid) {
		JSONObject feedback = new JSONObject();
		Collector bookl = userMapper.askcollector(uid);
		if (bookl == null) {
			feedback.put("body", "");
		} else {
			for (Book book:bookl.getBooks()) {
				Query query = new Query(Criteria.where("id").is(book.getBid()));
				BookImg bookImg = mongoTemplate.findOne(query, BookImg.class, "bookimg");
				book.setImg(bookImg.getImg());
			}
			feedback.put("body", bookl.getBooks());
		}
		feedback.put("resp", "s");
		return feedback;
	}
	
	@Override
	public JSONObject askhistory(int uid) {
		JSONObject feedback = new JSONObject();
		Collector bookl = userMapper.askhistory(uid);
		if (bookl == null) {
			feedback.put("body", "");
		} else {
			for (Book book:bookl.getBooks()) {
				Query query = new Query(Criteria.where("id").is(book.getBid()));
				BookImg bookImg = mongoTemplate.findOne(query, BookImg.class, "bookimg");
				if (bookImg != null) {
					book.setImg(bookImg.getImg());
				}
			}
			feedback.put("body", bookl.getBooks());
		}
		feedback.put("resp", "s");
		return feedback;
	}
	
	@Override
	public JSONObject askcomment(int uid) {
		JSONObject feedback = new JSONObject();
		List<Comment> commentl = userMapper.askcomment(uid);
		
		Query query2 = new Query(Criteria.where("id").is(uid));
		User user = userMapper.askuser(uid);
		UserDetl userDetl = mongoTemplate.findOne(query2, UserDetl.class, "user");
		
		for (Comment comment:commentl) {
			Query query = new Query(Criteria.where("id").is(comment.getCid()));
			CommentDes result=mongoTemplate.findOne(query, CommentDes.class, "comment");
			if (result != null) {
				comment.setDes(result.getDes());
			}
			if (userDetl != null) {
				comment.setImg(userDetl.getImg());
			}
			comment.setNickname(user.getNickname());
			
			Book book = bookMapper.askbookinfo(comment.getBid());
			if (book != null) {
				comment.setBookname(book.getBookname());
			}
		}
		
		feedback.put("body", commentl);
		return feedback;
	}

	@Override
	public JSONObject askworks(int uid) {
		JSONObject feedback = new JSONObject();
		List<Book> bookl = userMapper.askworks(uid);
		if (bookl == null) {
			feedback.put("body", "");
		} else {
			for (Book book:bookl) {
				Query query = new Query(Criteria.where("id").is(book.getBid()));
				BookImg bookImg = mongoTemplate.findOne(query, BookImg.class, "bookimg");
				if (bookImg != null) {
					book.setImg(bookImg.getImg());
				}
			}
			feedback.put("body", bookl);
		}
		feedback.put("resp", "s");
		return feedback;
	}

	@Override
	public JSONObject askselfworks(int uid) {
		JSONObject feedback = new JSONObject();
		List<Book> bookl = userMapper.askselfworks(uid);
		if (bookl == null) {
			feedback.put("body", "");
		} else {
			for (Book book:bookl) {
				Query query = new Query(Criteria.where("id").is(book.getBid()));
				BookImg bookImg = mongoTemplate.findOne(query, BookImg.class, "bookimg");
				if (bookImg != null) {
					book.setImg(bookImg.getImg());
				}
			}
			feedback.put("body", bookl);
		}
		feedback.put("resp", "s");
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

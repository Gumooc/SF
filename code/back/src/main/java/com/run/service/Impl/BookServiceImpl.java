package com.run.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.run.dao.BookDao;
import com.run.entity.Book;
import com.run.entity.BookAudio;
import com.run.entity.Comment;
import com.run.service.BookService;

import net.sf.json.JSONObject;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookDao bookMapper;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Book askbookinfo(int bid) {
		return bookMapper.askbookinfo(bid);
	}
	
	@Override
	public List<Book> askbooklist(){
		return bookMapper.askbooklist();
	}
	
	@Override
	public List<Book> searchbytitle(String bookname){
		System.out.println("service:"+bookname);
		return bookMapper.searchbytitle(bookname);
	}
	
	@Override
	public List<Comment> askcomment(int bid) {
		return bookMapper.askcomment(bid);
	}

	@Override
	public JSONObject insertBook(Book book) {
		JSONObject feedback = new JSONObject();
		bookMapper.insertBook(book);
		feedback.put("resp", "s");
		return feedback;
	}
	
	@Override
	public JSONObject deleteBook(int bid) {
		JSONObject feedback = new JSONObject();
		bookMapper.deleteBook(bid);
		feedback.put("resp", "s");
		return feedback;
	}
	
	@Override
	public JSONObject askaudio(int bid, int index) {
		JSONObject feedback = new JSONObject();
		Query query = new Query(Criteria.where("id").is(bid).and("index").is(index));
		BookAudio result=mongoTemplate.findOne(query, BookAudio.class, "book");
		
		if (result==null) {
			feedback.put("resp", "f");
			feedback.put("body", "");
		} else {
			feedback.put("resp", "s");
			feedback.put("body", result);
		}
		return feedback;
	}
	
	@Override
	public JSONObject insaudio(int bid, int index, MultipartFile audio) {
		JSONObject feedback = new JSONObject();
		BookAudio bookAudio = new BookAudio();
		bookAudio.setId(bid);
		bookAudio.setIndex(index);
		bookAudio.setAudio(audio);
		mongoTemplate.save(bookAudio,"book");
		feedback.put("resp", "s");
		return feedback;
	}
	
	@Override
	public JSONObject delaudio(int bid, int index) {
		JSONObject feedback = new JSONObject();
		Query query = new Query(Criteria.where("id").is(bid).and("index").is(index));
		if (query != null) {
			mongoTemplate.remove(query, BookAudio.class);
			feedback.put("resp", "s");
		} else {
			feedback.put("resp", "f");
		}
		return feedback;
	}
}

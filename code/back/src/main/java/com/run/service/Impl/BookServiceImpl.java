package com.run.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.multipart.MultipartFile;

import com.run.dao.BookDao;
import com.run.entity.Book;
import com.run.entity.BookAudio;
import com.run.entity.Comment;
import com.run.service.BookService;

import net.sf.json.JSONObject;

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
		return searchbytitle(bookname);
	}
	
	@Override
	public List<Comment> askcomment(int bid) {
		return bookMapper.askcomment(bid);
	}

	@Override
	public void insertBook(Book book) {
		bookMapper.insertBook(book);
	}
	
	@Override
	public void deleteBook(int bid) {
		bookMapper.deleteBook(bid);
	}
	
	@Override
	public JSONObject askaudio(int bid) {
		JSONObject feedback = new JSONObject();
		Query query = new Query(Criteria.where("id").is(bid));
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
	public void insaudio(int bid, MultipartFile audio) {
		//
	}
	
	@Override
	public void delaudio(int bid) {
		//
	}
}

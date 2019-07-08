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
import com.run.dao.BookDao;
import com.run.entity.Book;
import com.run.entity.BookAudio;
import com.run.entity.BookImg;
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
		Book book = bookMapper.askbookinfo(bid);
		Query query = new Query(Criteria.where("id").is(bid));
		BookImg result=mongoTemplate.findOne(query, BookImg.class, "bookimg");
		if (result != null) {
			book.setImg(result.getImg());
		}
		return book;
	}
	
	@Override
	public List<Book> askbooklist(){
		List<Book> booklist = bookMapper.askbooklist();
		for (Book book:booklist) {
			Query query = new Query(Criteria.where("id").is(book.getBid()));
			BookImg result=mongoTemplate.findOne(query, BookImg.class, "bookimg");
			if (result != null) {
				book.setImg(result.getImg());
			}
		}
		return booklist;
	}
	
	@Override
	public List<Book> searchbytitle(String bookname){
		List<Book> booklist = bookMapper.askbooklist();
		for (Book book:booklist) {
			Query query = new Query(Criteria.where("id").is(book.getBid()));
			BookImg result=mongoTemplate.findOne(query, BookImg.class, "bookimg");
			book.setImg(result.getImg());
		}
		return booklist;
	}
	
	@Override
	public List<Comment> askcomment(int bid) {
		return bookMapper.askcomment(bid);
	}

	@Override
	public JSONObject insertBook(Book book) {
		JSONObject feedback = new JSONObject();
		int bid = bookMapper.getmaxid()+1;
		book.setBid(bid);
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
	public JSONObject updateBook(Book book) {
		JSONObject feedback = new JSONObject();
		bookMapper.updateBook(book);
		feedback.put("resp", "s");
		return feedback;
	}
	

	@Override
	public JSONObject setBookimg(int bid, MultipartFile img) {
		JSONObject feedback = new JSONObject();
		String data = "";
		try {
			data = Base64.byteArrayToBase64(img.getBytes());
			BookImg bookImg = new BookImg();
			bookImg.setId(bid);
			bookImg.setImg(data);
			mongoTemplate.save(bookImg,"bookimg");
			feedback.put("resp", "s");
			feedback.put("body", "");
		} catch (IOException e) {

			feedback.put("resp", "f");
			feedback.put("body", "");
			e.printStackTrace();
		}
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
		mongoTemplate.remove(query, BookAudio.class);
		feedback.put("resp", "s");
		return feedback;
	}
}

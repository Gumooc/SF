package com.run.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.run.entity.Book;
import com.run.entity.Comment;

import net.sf.json.JSONObject;

public interface BookService {
	Book askbookinfo(int bid);
	List<Book> askbooklist();
	List<Book> searchbytitle(String bookname);
	List<Comment> askcomment(int bid); 
	
	JSONObject insertBook(Book book);
	JSONObject deleteBook(int bid);
	JSONObject updateBook(Book book);
	JSONObject setBookimg(int bid, MultipartFile img);
	
	JSONObject askaudio(int bid, int index);
	JSONObject delaudio(int bid, int index);
	JSONObject insaudio(int bid, int index, MultipartFile audio);
	
	JSONObject insDes(int bid, String des);
	JSONObject delDes(int bid);
	JSONObject askDes(int bid);
}
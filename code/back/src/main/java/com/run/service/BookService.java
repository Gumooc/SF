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
	
	void insertBook(Book book);
	void deleteBook(int bid);
	
	JSONObject askaudio(int bid);
	void delaudio(int bid);
	void insaudio(int bid, MultipartFile audio);
}

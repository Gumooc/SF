package com.run.dao;

import java.util.List;

import com.run.entity.Book;
import com.run.entity.Comment;

public interface BookDao {
	Book askbookinfo(int bid);
	List<Book> askbooklist();
	List<Book> searchbytitle(String bookname);
	List<Comment> askcomment(int bid); 
	
	void insertBook(Book book);
	void deleteBook(int bid);
	
}

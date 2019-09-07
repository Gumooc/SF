package com.run.dao;

import java.util.List;

import com.run.entity.Book;
import com.run.entity.Comment;
import com.run.entity.UserbookItem;

public interface BookDao {
	Book askbookinfo(int bid);
	List<Book> askbooklist();
	List<Book> searchbytitle(String bookname);
	List<Comment> askcomment(int bid); 
	
	void insertbook(Book book);
	void deletebook(int bid);
	void updatebook(Book book);
	
	Book collectcheck(UserbookItem ubi);
	
	void playbook(int bid);
	
	void setkind(Book book);
	String getauthor(int uid);
	String getBookname(int bid);
	
}

package com.run.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.run.entity.Book;
import com.run.entity.Collector;
import com.run.entity.Comment;
import com.run.entity.User;

@Repository
public interface UserDao {
	User logincheck(User user);
	Collector askcollector(int uid); 
	Collector askhistory(int uid);
	List<Comment> askcomment(int uid);
	List<Book> askworks(int uid);
}

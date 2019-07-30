package com.run.dao;

import java.util.List;

import com.run.entity.Book;

public interface RecommendDao {

	List<Book> asklatest(int num); 
	List<Book> askhottest(int num);
}

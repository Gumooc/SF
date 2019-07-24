package com.run.service;

import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;

import com.run.entity.Book;

public interface RecommendService {

	List<Book> recommend(int uid) throws TasteException, IOException;
}

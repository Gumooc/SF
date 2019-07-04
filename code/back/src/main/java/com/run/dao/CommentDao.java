package com.run.dao;

import org.springframework.stereotype.Repository;

import com.run.entity.Comment;

@Repository
public interface CommentDao {
	void insert(Comment comment);
	void delete(Comment comment);
}

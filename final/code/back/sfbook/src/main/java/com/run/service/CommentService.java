package com.run.service;

import com.run.entity.Comment;

import net.sf.json.JSONObject;

public interface CommentService {
	JSONObject insert(Comment comment);
	JSONObject delete(Comment comment);
}

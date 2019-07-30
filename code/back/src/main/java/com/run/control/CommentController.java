package com.run.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.run.entity.Comment;
import com.run.service.CommentService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	private void setRHeader(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials","true");
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public JSONObject commentins(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		JSONObject commentinfo = JSONObject.fromObject(liString);
		Comment comment = (Comment) JSONObject.toBean(commentinfo,Comment.class);
		feedback = commentService.insert(comment);
		return feedback;
	}
	

	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject commentdel(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		JSONObject commentinfo = JSONObject.fromObject(liString);
		Comment comment = (Comment) JSONObject.toBean(commentinfo,Comment.class);
		feedback = commentService.delete(comment);
		System.out.println(feedback);
		return feedback;
	}
}

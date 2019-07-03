package com.run.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.run.entity.Email;
import com.run.entity.User;
import com.run.service.EmailService;
import com.run.service.UserService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private EmailService emailService;
	
	private void setRHeader(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials","true");
	}
	
	@ResponseBody
	@RequestMapping("/login")
	public JSONObject handlelogin(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject userinfo = JSONObject.fromObject(liString);
		JSONObject feedback = new JSONObject();
		User user = new User();
		user.setUsername(userinfo.getString("username"));
		user.setPassword(userinfo.getString("password")); 
		System.out.println(user);
		feedback = userService.logincheck(user);
		return feedback;
	}
	
	
	@ResponseBody
	@RequestMapping("/register")
	public JSONObject handleregister(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject userinfo = JSONObject.fromObject(liString);
		JSONObject feedback = new JSONObject();
		User user = new User();
		user = (User) JSONObject.toBean(userinfo,User.class);
		//user.setEmail(userinfo.getString("email"));
		feedback = userService.register(user);
		if (feedback.getString("resp").equals("s")) {
			user.setUid(feedback.getJSONObject("body").getInt("uid"));
			emailService.sendmail(user);
		}
		return feedback;
	}

	@ResponseBody
	@RequestMapping("/activation")
	public JSONObject handleactivation(HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		int uid = Integer.valueOf(request.getParameter("uid"));
		String active = request.getParameter("active");
		JSONObject feedback = new JSONObject();
		feedback = userService.useractive(uid);
		return feedback;
	}
	
	@ResponseBody
	@RequestMapping("/collector")
	public JSONObject askcollector(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		int uid = JSONObject.fromObject(liString).getInt("uid");
		feedback = userService.askcollector(uid);
		return feedback;
	}
	@ResponseBody
	@RequestMapping("/history")
	public JSONObject askhistory(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		int uid = JSONObject.fromObject(liString).getInt("uid");
		feedback = userService.askhistory(uid);
		return feedback;
	}
	@ResponseBody
	@RequestMapping("/comment")
	public JSONObject askcomment(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		int uid = JSONObject.fromObject(liString).getInt("uid");
		feedback = userService.askcomment(uid);
		return feedback;
	}
	@ResponseBody
	@RequestMapping("/works")
	public JSONObject askworks(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		int uid = JSONObject.fromObject(liString).getInt("uid");
		feedback = userService.askworks(uid);
		return feedback;
	}
}

package com.run.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	@RequestMapping("/tplogin")
	public JSONObject tplogin(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		JSONObject userjs = JSONObject.fromObject(liString);/*
		String openid = userjs.getString("openid");
		String nickname = userjs.getString("nickname");
		boolean male = userjs.getString("gender").equals("m");*/
		feedback.put("uid", userService.tplogin(userjs));
		return feedback;
	}
	
	@ResponseBody
	@RequestMapping("/mdfypassword")
	public JSONObject mdfypassword(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		int uid = JSONObject.fromObject(liString).getInt("uid");
		String oldp = JSONObject.fromObject(liString).getString("oldp");
		String newp = JSONObject.fromObject(liString).getString("newp");
		userService.mdfypassword(uid, oldp, newp);
		feedback.put("resp", "s");
		return feedback;
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

	@RequestMapping("/activation")
	public String handleactivation(HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		int uid = Integer.valueOf(request.getParameter("uid"));
		//String active = request.getParameter("active");
		//JSONObject feedback = new JSONObject();
		userService.useractive(uid);
		
		return "activation";
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

	@ResponseBody
	@RequestMapping("/selfworks")
	public JSONObject askselfworks(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		int uid = JSONObject.fromObject(liString).getInt("uid");
		feedback = userService.askselfworks(uid);
		return feedback;
	}
	
	@ResponseBody
	@RequestMapping("/setImg")
	public JSONObject setImg(@RequestParam(value="uid") int uid, @RequestParam(value="file", required = false) MultipartFile img,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		feedback = userService.setImg(uid, img);
		return feedback;
	}
	
	@ResponseBody
	@RequestMapping("/askuserinfo")
	public JSONObject askuserinfo(@RequestBody String liString, HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		int uid = JSONObject.fromObject(liString).getInt("uid");
		feedback = userService.askuser(uid);
		return feedback;
	}
	
	@ResponseBody
	@RequestMapping("/updateuser")
	public JSONObject updateuser(@RequestBody String liString, HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		//System.out.println(liString);
		User user = (User) JSONObject.toBean(JSONObject.fromObject(liString), User.class);
		//System.out.println("update_controller");
		//System.out.println(user);
		feedback = userService.updateuser(user);
		return feedback;
	}
	
	@ResponseBody
	@RequestMapping("/logout")
	public JSONObject logout(@RequestBody String liString, HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		HttpSession session = request.getSession();
		session.invalidate();
		feedback.put("resp", "s");
		return feedback;
	}
}

package com.run.control;

import java.util.Random;

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

import net.sf.json.JSONArray;
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
		HttpSession session = request.getSession();
		if (session.getAttribute("createlist")==null){
            JSONArray  createlist=new JSONArray();
            session.setAttribute("createlist",createlist);
        }
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
		feedback = userService.mdfypassword(uid, oldp, newp);
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
		if (feedback.getString("resp").equals("s")) {
			HttpSession session = request.getSession();
			if (session.getAttribute("createlist")==null){
	            JSONArray  createlist=new JSONArray();
	            session.setAttribute("createlist",createlist);
	        }
		}
		return feedback;
	}
	
	@ResponseBody
	@RequestMapping("/register")
	public JSONObject handleregister(@RequestBody String liString, HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject userinfo = JSONObject.fromObject(liString);
		JSONObject feedback = new JSONObject();
		User user = new User();
		user = (User) JSONObject.toBean(userinfo,User.class);
		//user.setEmail(userinfo.getString("email"));
		feedback = userService.register(user);
		if (feedback.getString("resp").equals("s")) {
			user.setUid(feedback.getJSONObject("body").getInt("uid"));
            int min = 30;
            int max = 50;
            int active = new Random().nextInt(max-min)+min;
            String content = "<html><head></head><body><h1>这是一封激活邮件,激活请点击以下链接</h1><h3><a href='http://localhost:8080/sfbook/user/activation?uid="
                    +user.getUid()+"&active="+"1" + "'>http://49.234.77.32:8080/sfbook/user/activation?uid=" +user.getUid() +"&active="+active
                    + "</href></h3></body></html>";
			emailService.sendmail(user, content);
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
	@RequestMapping("/modify1")
	public JSONObject modify1(@RequestBody String liString, HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		JSONObject userjs = JSONObject.fromObject(liString);
		String username = userjs.getString("username");
		String email = userjs.getString("email");
		User user = userService.modifycheck(username, email);
		if (user != null) {
			feedback.put("resp", "s");
			JSONObject feedbody = new JSONObject();
			feedbody.put("uid", user.getUid());
			feedback.put("body", feedbody);
			HttpSession session = request.getSession();
			String evcode = (String) session.getAttribute("vcode");
			if (evcode != null) {
				feedback.put("resp", "ex");
			} else {
				int min = 1000;
				int max = 9999;
		        int vcode = new Random().nextInt(max-min)+min;
				String content = "<html><head></head><body><h1>这是您的sfbook验证码, 30分钟内有效</h1><h3>"+vcode+"</h3></body></html>";
				emailService.sendmail(user, content);
				String svcode = Integer.toString(vcode);
				session.setAttribute("vcode", svcode);
			}
          
		} else {
			feedback.put("resp", "f");
		}
		return feedback;
	}

	@ResponseBody
	@RequestMapping("/modify2")
	public JSONObject modify2(@RequestBody String liString, HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		JSONObject userjs = JSONObject.fromObject(liString);
		String vcode = userjs.getString("vcode");
		String username = userjs.getString("username");
		String email = userjs.getString("email");
		String password = userjs.getString("password");
		System.out.println("modify2:"+0);
		User user = userService.modifycheck(username, email);
		int uid = user.getUid();
		System.out.println("modify2:"+1);
		HttpSession session = request.getSession();
		System.out.println("modify2:"+2);
		String evcode = (String) session.getAttribute("vcode");
		System.out.println("modify2:"+3);
		System.out.println("evcode:"+evcode);
		if (evcode == null) {
			feedback.put("resp", "to");
		} else {
			if (vcode.equals(evcode)) {
				System.out.println("modify2:"+4);
				feedback.put("resp", "s");
				userService.updatepassword(uid, password);
				session.removeAttribute("vcode");
			} else {
				feedback.put("resp", "f");
			}
		}
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
		//System.out.println("update_Controller0");
		User user = (User) JSONObject.toBean(JSONObject.fromObject(liString), User.class);
		//System.out.println("update_Controller1");
		feedback = userService.updateuser(user);
		return feedback;
	}

	@ResponseBody
	@RequestMapping("/askhide")
	public JSONObject askhide(@RequestBody String liString, HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		int uid = JSONObject.fromObject(liString).getInt("uid");
		feedback.put("resp", "s");
		feedback.put("body", userService.askhide(uid));
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

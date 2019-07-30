package com.run.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.run.entity.User;
import com.run.service.EmailService;
import com.run.service.UserService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/email")
public class EmailController {
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserService userService;
	
	private void setRHeader(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials","true");
	}
	
	@ResponseBody
	@RequestMapping("/send")
	public JSONObject send(@RequestBody String liString, HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		int uid = JSONObject.fromObject(liString).getInt("uid");
		User user = (User) JSONObject.toBean(userService.askuser(uid).getJSONObject("body"),User.class);
		if (user.getActivation()) {
			feedback.put("resp", "f");
		} else {
			user.setUid(uid);
			emailService.sendmail(user);
			feedback.put("resp", "s");
		}
		return feedback;
	}

}

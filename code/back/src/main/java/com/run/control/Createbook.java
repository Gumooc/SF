package com.run.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/createbook")
public class Createbook {
	private void setRHeader(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials","true");
	}
	
	@ResponseBody
	@RequestMapping("/bytext")
	public JSONObject bytext(@RequestParam(value="bid") int bid, @RequestParam(value="chapter") int chapter, @RequestParam(value="file", required = false) MultipartFile txt,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		feedback.put("resp", "");
		return feedback;
	}
}
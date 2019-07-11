package com.run.control;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.type.TimeZoneType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.Base64;

@Controller
@RequestMapping("/test")
public class test {
	
	private void setRHeader(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials","true");
	}
	
	@ResponseBody
	@RequestMapping("/echo")
	public String echo(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		String stt = "ÄãºÃÂð";
		System.out.println(liString);
		System.out.println(stt);
		return liString;
	}
	

	@ResponseBody
	@RequestMapping("/echot")
	public String echot(@RequestParam(value="bid") int bid, @RequestParam(value="file", required = false) MultipartFile txt,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		String stt = "ÄãºÃÂð";

		String data ="";
		try {
			data = new String(txt.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(data);
		return stt;
	}
}


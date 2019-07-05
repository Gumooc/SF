package com.run.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.run.entity.UserbookItem;
import com.run.service.CollectorService;
import com.run.service.UserService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/collector")
public class CollectorController {
	@Autowired
	private CollectorService collectorService;
	
	private void setRHeader(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials","true");
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public JSONObject inscollector(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		JSONObject ubijs = JSONObject.fromObject(liString);
		UserbookItem ubi = (UserbookItem) JSONObject.toBean(ubijs, UserbookItem.class);
		feedback = collectorService.insert(ubi);
		return feedback;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delcollector(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		JSONObject ubijs = JSONObject.fromObject(liString);
		UserbookItem ubi = (UserbookItem) JSONObject.toBean(ubijs, UserbookItem.class);
		feedback = collectorService.delete(ubi);
		return feedback;
	}
}

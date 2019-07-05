package com.run.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.run.entity.UserbookItem;
import com.run.service.HistoryService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/history")
public class HistoryController {
	@Autowired
	private HistoryService historyService;
	
	private void setRHeader(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials","true");
	}
	
	@ResponseBody
	@RequestMapping("/clear")
	public JSONObject clearhistory(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		JSONObject hisjs = JSONObject.fromObject(liString);
		feedback = historyService.clear(hisjs.getInt("uid"));
		return feedback;
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public JSONObject inserthistory(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		JSONObject hisjs = JSONObject.fromObject(liString);
		UserbookItem ubi = (UserbookItem) JSONObject.toBean(hisjs,UserbookItem.class);
		feedback = historyService.insert(ubi);
		return feedback;
	}
	
	
}

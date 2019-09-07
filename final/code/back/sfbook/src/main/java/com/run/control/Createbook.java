package com.run.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.run.service.BookService;
import com.run.service.CreateBookService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/createbook")
public class Createbook {
	@Autowired 
	private BookService bookService;
	@Autowired
	private CreateBookService createBookService;
	
	private void setRHeader(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials","true");
	}

	//spd	String	���٣�ȡֵ0-9��Ĭ��Ϊ5������
	//pit	String	������ȡֵ0-9��Ĭ��Ϊ5�����
	//vol	String	������ȡֵ0-15��Ĭ��Ϊ5������
	//per	String	������ѡ��, 0ΪŮ����1Ϊ������
	//3Ϊ��кϳ�-����ң��4Ϊ��кϳ�-��ѾѾ��Ĭ��Ϊ��ͨŮ
	@ResponseBody
	@RequestMapping("/bytext")
	public JSONObject bytext(@RequestParam(value="info") String info, @RequestParam(value="file", required = false) MultipartFile txt,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		JSONObject infojs = JSONObject.fromObject(info);
		JSONObject urljs = new JSONObject();
		try {
			urljs = createBookService.bytext(request.getSession(), infojs, txt);
			System.out.println(urljs);
			bookService.insaudio(infojs.getInt("bid"), infojs.getInt("chapter"), urljs.getString("audiopath"));
			feedback.put("resp", "s");
			feedback.put("body", urljs.getString("audiopath"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			feedback.put("resp", "f");
			System.out.println("text_failed");
			e.printStackTrace();
		}
		return feedback;
	}

	@ResponseBody
	@RequestMapping("/bysound")
	public JSONObject bysound(@RequestParam(value="info") String info, @RequestParam(value="file", required = false) MultipartFile txt,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		JSONObject infojs = JSONObject.fromObject(info);
		JSONObject urljs = new JSONObject();
		try {
			urljs = createBookService.bysound(request.getSession(), infojs, txt);
			System.out.println(urljs);
			bookService.insaudio(infojs.getInt("bid"), infojs.getInt("chapter"), urljs.getString("audiopath"));
			feedback.put("resp", "s");
			feedback.put("body", urljs.getString("audiopath"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			feedback.put("resp", "f");
			System.out.println("sound_failed");
			e.printStackTrace();
		}
		return feedback;
	}

	@ResponseBody
	@RequestMapping("/making")
	public JSONObject making(HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		HttpSession session = request.getSession();
		JSONArray createlist = (JSONArray) session.getAttribute("createlist");
		JSONArray bodylist = new JSONArray();
		if (createlist != null) {
			for (int i = 0; i < createlist.size(); i++) {
				JSONObject item = (JSONObject) createlist.get(i);
				item.put("bookname", bookService.getBookname(item.getInt("bid")));
				bodylist.add(item);
			}
		}
		feedback.put("resp", "s");
		feedback.put("body", bodylist);
		return feedback;
	}
}
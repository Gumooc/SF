package com.run.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.run.entity.Book;
import com.run.service.BookService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BookService bookService;
	
	private void setRHeader(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials","true");
	}
	
	@ResponseBody
	@RequestMapping("/bookinfo")
	public JSONObject bookinfo(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject bookinfo = JSONObject.fromObject(liString);
		JSONObject feedback = new JSONObject();
		int bid = bookinfo.getInt("bid");
		int uid = bookinfo.getInt("uid");
		Book book = bookService.askbookinfo(bid);
		if (book == null) {
			feedback.put("resp", "f");
			feedback.put("body", "");
		} else {
			if (uid>0) {
				book.setCollected(bookService.collectcheck(uid, bid));
			} else {
				book.setCollected(false);
			}
			feedback.put("resp", "s");
			feedback.put("body", book);
		}
		return feedback;
	}

	@ResponseBody
	@RequestMapping("/booklist")
	public JSONObject booklist(HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		List<Book> booklist = bookService.askbooklist();
		feedback.put("resp", "s");
		feedback.put("body", booklist);
		return feedback;
	}

	@ResponseBody
	@RequestMapping("/chapter")
	public JSONObject chapter(@RequestBody String liString, HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		int bid = JSONObject.fromObject(liString).getInt("bid");
		String chapter = JSONObject.fromObject(liString).getString("chapter");
		bookService.updatechapter(bid, chapter);
		return feedback;
	}
	
	@ResponseBody
	@RequestMapping("/search")
	public JSONObject search(@RequestBody String liString, HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		String bookname = JSONObject.fromObject(liString).getString("bookname");
		System.out.println(bookname);
		List<Book> booklist = bookService.searchbytitle(bookname);
		if (booklist == null) {
			feedback.put("resp", "f");
			feedback.put("body", "");
		} else {
			feedback.put("resp", "s");
			feedback.put("body", booklist);
		}
		return feedback;
	}
	

	@ResponseBody
	@RequestMapping("/comment")
	public JSONObject askcomment(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		int bid = JSONObject.fromObject(liString).getInt("bid");
		feedback = bookService.askcomment(bid);
		return feedback;
	}
	

	@ResponseBody
	@RequestMapping("/insertbook")
	public JSONObject insbook(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		Book book = (Book) JSONObject.toBean(JSONObject.fromObject(liString), Book.class);
		feedback = bookService.insertBook(book);
		return feedback;
	}

	@ResponseBody
	@RequestMapping("/deletebook")
	public JSONObject delbook(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		int bid = JSONObject.fromObject(liString).getInt("bid");
		feedback = bookService.deleteBook(bid);
		
		return feedback;
	}

	@ResponseBody
	@RequestMapping("/updatebook")
	public JSONObject updbook(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		Book book = (Book) JSONObject.toBean(JSONObject.fromObject(liString), Book.class);
		feedback = bookService.updateBook(book);
		
		return feedback;
	}
	

	@ResponseBody
	@RequestMapping("/setImg")
	public JSONObject setImg(@RequestParam(value="bid") int bid, @RequestParam(value="file", required = false) MultipartFile img,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		feedback = bookService.setBookimg(bid, img);
		return feedback;
	}
	
	@ResponseBody
	@RequestMapping("/createbookbytext")
	public JSONObject textbook(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		return feedback;
	}
	
	@ResponseBody
	@RequestMapping("/askaudio")
	public JSONObject audiobook(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		JSONObject bookinfo = JSONObject.fromObject(liString);
		int bid = bookinfo.getInt("bid");
		int index = bookinfo.getInt("index");
		feedback = bookService.askaudio(bid, index);
		return feedback;
	}
	
	@ResponseBody
	@RequestMapping("/delaudio")
	public JSONObject delaudio(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		JSONObject bookinfo = JSONObject.fromObject(liString);
		int bid = bookinfo.getInt("bid");
		int index = bookinfo.getInt("index");
		feedback = bookService.delaudio(bid, index);
		return feedback;
	}
	
	@ResponseBody
	@RequestMapping("/insaudio")
	public JSONObject insaudio(@RequestParam(value="bid") int bid, @RequestParam(value="index") int index, @RequestParam(value="file", required = false) MultipartFile audio,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();

		String data ="";
		try {
			data = new String(audio.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		feedback = bookService.insaudio(bid, index, data);
		return feedback;
	}
	
	@ResponseBody
	@RequestMapping("/insbookdes")
	public JSONObject insbookdes(@RequestBody String liString, HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		int bid = JSONObject.fromObject(liString).getInt("bid");
		String des = JSONObject.fromObject(liString).getString("des");
		feedback = bookService.insDes(bid, des);
		return feedback;
	}

	@ResponseBody
	@RequestMapping("/askbookdes")
	public JSONObject askbookdes(@RequestBody String liString, HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		JSONObject feedback = new JSONObject();
		int bid = JSONObject.fromObject(liString).getInt("bid");
		feedback = bookService.askDes(bid);
		return feedback;
	}
}

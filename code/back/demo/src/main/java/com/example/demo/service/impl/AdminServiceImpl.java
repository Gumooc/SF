package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.entity.Book;
import com.example.demo.service.AdminService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public JSONObject checkAdmin(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        JSONObject jsonObject = new JSONObject();
        if(userDao_check(username, password)){
            jsonObject.put("user", username);
            jsonObject.put("token", true);
            jsonObject.put("isBanned", false);
            jsonObject.put("isAuthorized", true);
        }else{
            jsonObject.put("user", username);
            jsonObject.put("token", false);
            jsonObject.put("isBanned", false);
            jsonObject.put("isAuthorized", true);
        }
        return jsonObject;
    }

    @Override
    public JSONObject userList(){
        User user = userDao_list();
        JSONObject jsonObject = new JSONObject();
        JSONObject tabledata = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        tabledata.put("username", user.getUsername());
        tabledata.put("email", user.getEmail());
        tabledata.put("phone", user.getPhone());
        tabledata.put("authorized", false);
        tabledata.put("banned", true);
        jsonArray.add(tabledata);
        jsonObject.put("tabledata", jsonArray);

        return jsonObject;
    }

    @Override
    public JSONObject bookList(){
        Book book = bookDao_list();
        JSONObject jsonObject = new JSONObject();
        JSONObject tabledata = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        tabledata.put("book_id", book.getBid());
        tabledata.put("bookname", book.getBookname());
        String creator = userDao_findOne(book.getUid());
        tabledata.put("creator", creator);
        tabledata.put("shared", book.getShared());
        tabledata.put("bookclass", book.getKind());
        jsonObject.put("tabledata", tabledata);
        jsonArray.add(tabledata);
        jsonObject.put("tabledata", jsonArray);

        System.out.println(jsonObject);

        return jsonObject;
    }

    //necessary api
    private boolean userDao_check(String username, String password){
        return true;
    }

    private String userDao_findOne(int book_id){
        return "hzt";
    }

    private User userDao_list(){
        User user = new User();
        user.setUsername("hzt");
        user.setUid(102044);
        user.setEmail("hzt@sina.com");
        user.setPhone("11111111111");
        return user;
    }

    private Book bookDao_list(){
        Book book = new Book();
        book.setBid(1001);
        book.setUid(102044);
        book.setBookname("日记");
        book.setShared(false);
        book.setKind("成人");
        return book;
    }
}

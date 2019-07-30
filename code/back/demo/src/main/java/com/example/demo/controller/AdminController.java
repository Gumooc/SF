package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.AdminService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@CrossOrigin
@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping(value = "/AdminLogin")
    public JSONObject adminLogin(@RequestBody Map<String, Object> request){
        String username = (String)request.get("username");
        String password = (String)request.get("password");
        System.out.println(username);
        System.out.println(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        return adminService.checkAdmin(user);
    }

    @PostMapping(value = "/AdminUserList")
    public JSONObject adminUserList(){
        return adminService.userList();
    }

    @PostMapping(value = "/AdminBookList")
    public JSONObject adminBookList(){
        return adminService.bookList();
    }

    @PostMapping(value = "/AdminBanBook")
    public JSONObject adminBanBook(@RequestBody Map<String, Object> request){
        int book_id = (int)request.get("book_id");
    }

    @PostMapping(value = "/AdminUpdateBook")
    public JSONObject adminUpdateBook(@RequestBody Map<String, Object> request){
        int book_id = (int)request.get("book_id");
        return new JSONObject();
    }

    @PostMapping(value = "/AdminRecoverBook")
    public JSONObject adminRecoverBook(@RequestBody Map<String, Object> request){
        int book_id = (int)request.get("book_id");
    }

    @PostMapping(value = "/AdminBanOperator")
    public JSONObject adminBanOperator(@RequestBody Map<String, Object> request){
        String username = (String) request.get("username");
        Boolean flag = (Boolean)request.get("banned");
    }
}

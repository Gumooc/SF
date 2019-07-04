package com.example.demo.service;

import com.example.demo.entity.User;
import net.sf.json.JSONObject;

public interface AdminService {
    JSONObject checkAdmin(User user);

    JSONObject userList();

    JSONObject bookList();
}

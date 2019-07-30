package com.example.demo.entity;

import java.sql.Date;
public class User {
    private int uid;
    private String username;
    private String password;
    private Date lst;
    private String img;
    private Boolean male;
    private String phone;
    private String email;
    private boolean activation;

    public void setActivation(boolean activation) {
        this.activation = activation;
    }

    public boolean getActivation() {
        return activation;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return this.uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return this.img;
    }

    public void setLst(Date lst) {
        this.lst = lst;
    }

    public Date getLst() {
        return this.lst;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public Boolean getMale() {
        return male;
    }
}

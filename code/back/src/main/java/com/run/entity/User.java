package com.run.entity;

public class User {
	private int uid;
	private String username;
	private String password;
	private String nickname;
	private String lst;
	private String rgt;
	private String birth;
	private String img;
	private boolean male;
	private String phone;
	private String email;
	private boolean activation;
	private boolean adm;

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	public String getBirth() {
		return birth;
	}
	
	public void setAdm(boolean adm) {
		this.adm = adm;
	}
	
	public boolean getAdm() {
		return adm;
	}
	
	public void setRgt(String rgt) {
		this.rgt = rgt;
	}
	
	public String getRgt() {
		return this.rgt;
	}
	
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
	
	public void setLst(String lst) {
		this.lst = lst;
	}
	
	public String getLst() {
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
	
	@Override
	public String toString() {
		return "User = ["+"uid="+uid+",username="+username+",password="+password+"email="+email+",lst="+lst+"]";
	}
}

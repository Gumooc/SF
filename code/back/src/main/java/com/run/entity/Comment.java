package com.run.entity;

public class Comment {
	private int bid;
	private int cid; 
	private int uid;
	private String nickname;
	private String bookname;
	private String lst;
	private String des;
	private String img;
	
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	
	public String getBookname() {
		return bookname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setImg(String img) {
		this.img = img;
	}
	
	public String getImg() {
		return img;
	}
	
	public void setDes(String des) {
		this.des = des;
	}
	
	public String getDes() {
		return des;
	}
	
	public void setBid(int bid) {
		this.bid = bid;
	}
	
	public int getBid() {
		return this.bid;
	}
	
	public void setCid(int cid) {
		this.cid = cid;
	}
	
	public int getCid() {
		return this.cid;
	}
	
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public int getUid() {
		return uid;
	}

	public String getLst() {
		return this.lst;
	}
	
	public void setLst(String lst) {
		this.lst = lst;
	}

	@Override
	public String toString() {
		return "Comment = ["+"cid="+cid+"uid="+uid+",bid="+bid+",bookname="+bookname+",lst="+lst+"]";
	}
}

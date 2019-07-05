package com.run.entity;

import java.sql.Date;

public class Book {
	private int bid;
	private int uid;
	private String kind;
	private String txt;
	private String sound;
	private String img;
	private Date lst;
	
	public int getBid() {
		return this.bid;
	}
	
	public void setBid(int bid) {
		this.bid = bid;
	}
	
	public int getUid() {
		return this.uid;
	}
	
	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getKind() {
		return this.kind;
	}
	
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public String getTxt() {
		return this.txt;
	}
	
	public void setTxt(String txt) {
		this.txt = txt;
	}
	

	public String getSound() {
		return this.sound;
	}
	
	public void setSound(String sound) {
		this.sound = sound;
	}

	public String getImg() {
		return this.img;
	}
	
	public void setImg(String img) {
		this.img = img;
	}
	
	public Date getLst() {
		return this.lst;
	}
	
	public void setLst(Date lst) {
		this.lst = lst;
	}
	
	@Override
	public String toString() {
		return "Book = ["+"bid="+bid+",uid="+uid+",lst="+lst+"]";
	}
}

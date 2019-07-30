package com.run.entity;

public class Comment {
	private int bid;
	private int cid; 
	private int uid;
	private String lst;
	private String des;
	
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
}

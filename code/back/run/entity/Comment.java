package com.run.entity;

import java.sql.Date;
//弱实体类、主键（bid、cid）
public class Comment {
	private int bid;
	private int cid; 
	private int uid;
	private Date lst;
	
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

	public Date getLst() {
		return this.lst;
	}
	
	public void setLst(Date lst) {
		this.lst = lst;
	}
}

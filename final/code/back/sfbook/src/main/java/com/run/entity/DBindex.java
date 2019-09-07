package com.run.entity;

public class DBindex {
	private String id;
	private int value;
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	@Override
	public String toString() {
		return "DBindex = ["+"id="+id+",value="+value+"]";
	}
}

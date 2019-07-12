package com.run.entity;

import org.springframework.web.multipart.MultipartFile;

public class BookAudio {
	private int id;
	private int index;
	private String audio;
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setAudio(String audio) {
		this.audio = audio;
	}
	
	public String getAudio() {
		return audio;
	}
}

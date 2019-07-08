package com.run.entity;

import org.springframework.web.multipart.MultipartFile;

public class BookAudio {
	private int id;
	private int index;
	private MultipartFile audio;
	
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
	
	public void setAudio(MultipartFile audio) {
		this.audio = audio;
	}
	
	public MultipartFile getAudio() {
		return audio;
	}
}

package com.run.entity;

import java.util.List;

public class Collector {
	private int uid;
	private List<Book> books;

	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public int getUid() {
		return this.uid;
	}
	public List<Book> getBooks() {
		return this.books;
	}
	
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public void addBook(Book book) {
		this.books.add(book);
	}
}

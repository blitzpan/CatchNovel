package com.novel.entity;

public class UserBook {
	private String id;
	private String userId;
	private String email;
	private String bookId;
	private String sendMail;
	
	@Override
	public String toString() {
		return "UserBook [id=" + id + ", userId=" + userId + ", email=" + email + ", bookId=" + bookId + ", sendMail="
				+ sendMail + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getSendMail() {
		return sendMail;
	}
	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}

package com.novel.entity;
/**
 * 邮件发送任务实体类
 * @author admin
 *
 */
public class SendMailTask {
	private String id;
	private String bookId;
	private String status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}

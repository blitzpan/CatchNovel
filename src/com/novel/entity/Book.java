package com.novel.entity;

public class Book {
	private String id;
	private String bookId;
	private String pageNum;
	private String content;
	private String gatherDate;
	private int sendMail;
		
	@Override
	public String toString() {
		return "Book [id=" + id + ", bookId=" + bookId + ", pageNum=" + pageNum + ", content=" + content
				+ ", gatherDate=" + gatherDate + ", sendMail=" + sendMail + "]";
	}
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
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getGatherDate() {
		return gatherDate;
	}
	public void setGatherDate(String gatherDate) {
		this.gatherDate = gatherDate;
	}
	public int getSendMail() {
		return sendMail;
	}
	public void setSendMail(int sendMail) {
		this.sendMail = sendMail;
	}
}

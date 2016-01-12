package com.novel.entity;

public class Chapter {
	private String id;
	private String bookInfoId;
	private String pageNum;
	private String content;
	private String url;
	private String gatherDate;
	private int sendMail;
		
	@Override
	public String toString() {
		return "Book [id=" + id + ", bookInfoId=" + bookInfoId + ", pageNum=" + pageNum + ", content=" + content
				+ ", gatherDate=" + gatherDate + ", sendMail=" + sendMail + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBookInfoId() {
		return bookInfoId;
	}
	public void setBookInfoId(String bookInfoId) {
		this.bookInfoId = bookInfoId;
	}
}

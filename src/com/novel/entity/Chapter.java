package com.novel.entity;

public class Chapter {
	private String id;
	private String bookInfoId;
	private String pageNum;
	private String content;
	private int len;
	private String url;
	private String gatherDate;
	private int sendMail;
	//和书籍信息有关
	private String authorName;
	private String articleName;
	private String homeUrl;
		
	
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
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getArticleName() {
		return articleName;
	}
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}
	public String getHomeUrl() {
		return homeUrl;
	}
	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	@Override
	public String toString() {
		return "Chapter [id=" + id + ", bookInfoId=" + bookInfoId + ", pageNum=" + pageNum + ", content=" + content
				+ ", len=" + len + ", url=" + url + ", gatherDate=" + gatherDate + ", sendMail=" + sendMail
				+ ", authorName=" + authorName + ", articleName=" + articleName + ", homeUrl=" + homeUrl + "]";
	}
}

package com.novel.entity;

import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.novel.util.ComUtils;

public class Tianya {
	private String id;
	private String bookId;
	private String authorId;
	private String authorName;
	private String articleName;
	private String articleId;
	private String articleUrl;
	private String homeUrl;
	private String pageNum;
	private String state;
	/**
	 * 根据正则url和页码获取真实的页码
	 * @return
	 */
	public String getRealUrl(){
		return articleUrl.replace("pageNum", pageNum);
	}
	/**
	 * 页码加一
	 */
	public void pageNumAdd(){
		pageNum = "" + (ComUtils.parseInt(pageNum) + 1);//页码+1
	}
	
	@Override
	public String toString() {
		return "Tianya [id=" + id + ", bookId=" + bookId + ", authorId=" + authorId + ", authorName=" + authorName
				+ ", articleName=" + articleName + ", articleId=" + articleId + ", articleUrl=" + articleUrl
				+ ", homeUrl=" + homeUrl + ", pageNum=" + pageNum + ", state=" + state + "]";
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
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
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
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getArticleUrl() {
		return articleUrl;
	}
	public void setArticleUrl(String articleUrl) {
		this.articleUrl = articleUrl;
	}
	public String getHomeUrl() {
		return homeUrl;
	}
	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}

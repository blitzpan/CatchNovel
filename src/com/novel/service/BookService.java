package com.novel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.BookDao;
import com.novel.dao.TianyaDao;
import com.novel.entity.Book;
@Transactional
@Service
public class BookService {
	@Autowired 
	private BookDao bookDao;
	@Autowired
	private TianyaDao tianyaDao;
	/**
	 * 非事务方式运行，读取已经commit的数据
	 * @param book
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,isolation=Isolation.READ_COMMITTED,readOnly=true)
	public List queryBooks(Book book) throws Exception{
		return bookDao.queryBooks(book);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void addBook(String id) throws Exception{
		System.out.println("插入数据=" + id);
	}
	
	public BookDao getBookDao() {
		return bookDao;
	}
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public TianyaDao getTianyaDao() {
		return tianyaDao;
	}

	public void setTianyaDao(TianyaDao tianyaDao) {
		this.tianyaDao = tianyaDao;
	}
}

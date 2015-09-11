package com.novel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.BookDao;
import com.novel.dao.TianyaDao;
@Transactional
@Service
public class BookService {
	@Autowired 
	private BookDao bookDao;
	@Autowired
	private TianyaDao tianyaDao;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void addBook(String id) throws Exception{
		System.out.println("≤Â»Î ˝æ›=" + id);
		bookDao.addBook(id);
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

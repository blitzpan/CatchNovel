package com.novel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.BookDao;

/**
 *关于事务学习的一个service
 */
@Transactional
@Service
public class TransactionalService {
	@Autowired 
	private BookDao bookDao;
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void addBook(String id) throws Exception{
		System.out.println("插入数据=" + id);
		bookDao.addBook(id);
	}
	
	public void addBooks() throws Exception{
		for(int i =1; i<6; i++){
			this.addBook(i+"");
		}
	}
	
	public BookDao getBookDao() {
		return bookDao;
	}
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
}

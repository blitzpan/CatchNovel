package com.novel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.BookDao;
import com.novel.dao.TianyaDao;

/**
 *关于事务学习的一个service
 *1.如果没有定义事务的话，批量插入每一次成功操作数据库的记录都将成功提交，只有出错的那一次才回滚了，其他的不会滚。
 *2.定义了事务，批量插入中只要有一次失败，那么就全部回滚。
 *3.<mark>狗日的，折腾了一下午，好像得出个这么个结论：SERVICE里方法调本类中的方法是不会另外再开新的事务的，
 *难道要为了实现事务而把一个业务的方法写到多个service里面</mark>
 */
@Transactional
@Service
public class TransactionalService {
	@Autowired 
	private BookDao bookDao;
	@Autowired
	private TianyaDao tianyaDao;
	@Autowired
	private BookService bookService;
	
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	/*public void addBook(String id) throws Exception{
		System.out.println("插入数据=" + id);
		bookDao.addBook(id);
	}*/
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void addBooks() throws Exception{
		this.addTianya();
		
	}
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void addTianya() throws Exception{
		System.out.println("ty插入="+tianyaDao.addTianya());
		for(int i =1; i<10; i++){
//			this.addBook(i+"");
			bookService.addBook(i+"");
		}
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
	public BookService getBookService() {
		return bookService;
	}
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
}

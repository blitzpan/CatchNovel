package com.novel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.BookDao;
import com.novel.dao.TianyaDao;
import com.novel.entity.Book;
import com.novel.entity.Tianya;
@Transactional
@Service
public class TianyaService {
	@Autowired 
	private BookDao bookDao;
	@Autowired
	private TianyaDao tianyaDao;
	/**
	 * 查询所有的天涯任务
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public List<Tianya> queryAll(Map para) throws Exception{
		return tianyaDao.queryAll(para);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void addBook(Tianya ty, Book book) throws Exception{
		bookDao.addBook(book);
		tianyaDao.updatePage(ty);
	}
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void bookDaotest() throws Exception{
		System.out.println("bookDao=" + bookDao);
		System.out.println("tianya.总共=" + bookDao.queryMax());
	}
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
//	@Transactional(propagation=Propagation.NESTED)
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void tianyaDaotest() throws Exception{
		System.out.println("tianya.总共=" + tianyaDao.queryMax());
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
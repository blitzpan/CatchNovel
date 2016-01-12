package com.novel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.ChapterDao;
import com.novel.dao.TianyaDao;
import com.novel.entity.Chapter;
@Transactional
@Service
public class ChapterService {
	@Autowired 
	private ChapterDao chapterDao;
	@Autowired
	private TianyaDao tianyaDao;
	@Autowired
	private TianyaService tianyaService;
	/**
	 * 非事务方式运行，读取已经commit的数据
	 * @param book
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,isolation=Isolation.READ_COMMITTED,readOnly=true)
	public List queryBooks(Chapter book) throws Exception{
		return chapterDao.queryChapters(book);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void addBook(String id) throws Exception{
		System.out.println("插入数据=" + id);
	}
	
	public ChapterDao getBookDao() {
		return chapterDao;
	}
	public void setBookDao(ChapterDao chapterDao) {
		this.chapterDao = chapterDao;
	}

	public TianyaDao getTianyaDao() {
		return tianyaDao;
	}

	public void setTianyaDao(TianyaDao tianyaDao) {
		this.tianyaDao = tianyaDao;
	}

	public TianyaService getTianyaService() {
		return tianyaService;
	}

	public void setTianyaService(TianyaService tianyaService) {
		this.tianyaService = tianyaService;
	}
}

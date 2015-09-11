package com.novel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.BookDao;
import com.novel.dao.TianyaDao;

/**
 *��������ѧϰ��һ��service
 *1.���û�ж�������Ļ�����������ÿһ�γɹ��������ݿ�ļ�¼�����ɹ��ύ��ֻ�г������һ�βŻع��ˣ������Ĳ������
 *2.��������������������ֻҪ��һ��ʧ�ܣ���ô��ȫ���ع���
 *3.<mark>���յģ�������һ���磬����ó�����ô�����ۣ�SERVICE�﷽���������еķ����ǲ��������ٿ��µ�����ģ�
 *�ѵ�ҪΪ��ʵ���������һ��ҵ��ķ���д�����service����</mark>
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
		System.out.println("��������=" + id);
		bookDao.addBook(id);
	}*/
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void addBooks() throws Exception{
		this.addTianya();
		
	}
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void addTianya() throws Exception{
		System.out.println("ty����="+tianyaDao.addTianya());
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

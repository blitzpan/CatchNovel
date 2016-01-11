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
	@Autowired
	private TianyaService tianyaService;
	/**
	 * ������ʽ���У���ȡ�Ѿ�commit������
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
		System.out.println("��������=" + id);
	}
	public void test() throws Exception{
		long begin = System.currentTimeMillis();
		System.out.println("normal insert begin:");
		bookDao.test();
		System.out.println("normal insert end. spend time = " + (System.currentTimeMillis() - begin) / 1000.0 );
//		System.out.println("�ܹ�=" + bookDao.queryMax());
		begin = System.currentTimeMillis();
		/*&
		System.out.println("batch insert begin:");
		bookDao.batchTest();
		System.out.println("batch insert end. spend time = " + (System.currentTimeMillis() - begin) / 1000.0 );
		*/
//		System.out.println("�ܹ�=" + bookDao.queryMax());
		
		System.out.println("bookDao=" + bookDao);
		//�������tianyaService���ж��Ƿ���һ������
		//һ��bookDaotest�������񴫲�����ΪREQUIRES_NEW������������쳣���˳�������ôbookService.test���յ��쳣���ͻ�ع�
		//�����������һ��try-catch��û���׳��쳣��bookService.test�Ͳ���ع�
		tianyaService.bookDaotest();
		/*try {
			tianyaService.bookDaotest();
		} catch (Exception e) {
			
		}*/
		//REQUIRES_NEW
//		tianyaService.tianyaDaotest();//REQUIRES_NEW��ʱ���ع����������񴫲�����ΪREQUIRES_NEW������������쳣���˳�������ôbookService.test���յ��쳣���ͻ�ع�
		/*try {
		 //REQUIRES_NEW
			tianyaService.tianyaDaotest();//REQUIRES_NEW��ʱ�����������һ��try-catch��û���׳��쳣��bookService.test�� ���� �ع�
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		//REQUIRES
//		tianyaService.tianyaDaotest();//tianyaDaotestû����������Ĭ��REQUIRES����ʱ���ع�
		/*try {
			//REQUIRES
			tianyaService.tianyaDaotest();//tianyaDaotestû����������Ĭ��REQUIRES����ʱ��Ҳ��ع�
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		//NESTED���������ʧ��ʱ����ع��ڲ����������Ķ��������ڲ��������ʧ�ܲ����������������Ļع�
//		tianyaService.tianyaDaotest();//tianyaDaotest������ع�
		/*try {
			//NESTED
			tianyaService.tianyaDaotest();////tianyaDaotest���ڲ�ʧ�ܣ����    ����    �ع�
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		//NOT_SUPPORTED
//		tianyaService.tianyaDaotest();//tianyaDaotest������ع�
		try {
			//NOT_SUPPORTED
			tianyaService.tianyaDaotest();////tianyaDaotest���ڲ�ʧ�ܣ����    ����    �ع�
		} catch (Exception e) {
			// TODO: handle exception
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

	public TianyaService getTianyaService() {
		return tianyaService;
	}

	public void setTianyaService(TianyaService tianyaService) {
		this.tianyaService = tianyaService;
	}
}

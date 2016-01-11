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
	public void test() throws Exception{
		long begin = System.currentTimeMillis();
		System.out.println("normal insert begin:");
		bookDao.test();
		System.out.println("normal insert end. spend time = " + (System.currentTimeMillis() - begin) / 1000.0 );
//		System.out.println("总共=" + bookDao.queryMax());
		begin = System.currentTimeMillis();
		/*&
		System.out.println("batch insert begin:");
		bookDao.batchTest();
		System.out.println("batch insert end. spend time = " + (System.currentTimeMillis() - begin) / 1000.0 );
		*/
//		System.out.println("总共=" + bookDao.queryMax());
		
		System.out.println("bookDao=" + bookDao);
		//这里调用tianyaService来判断是否是一个事务
		//一、bookDaotest设置事务传播机制为REQUIRES_NEW，这里如果把异常抛了出来，那么bookService.test接收到异常，就会回滚
		//而如果这里用一个try-catch，没有抛出异常，bookService.test就不会回滚
		tianyaService.bookDaotest();
		/*try {
			tianyaService.bookDaotest();
		} catch (Exception e) {
			
		}*/
		//REQUIRES_NEW
//		tianyaService.tianyaDaotest();//REQUIRES_NEW这时候会回滚。设置事务传播机制为REQUIRES_NEW，这里如果把异常抛了出来，那么bookService.test接收到异常，就会回滚
		/*try {
		 //REQUIRES_NEW
			tianyaService.tianyaDaotest();//REQUIRES_NEW这时候如果这里用一个try-catch，没有抛出异常，bookService.test就 不会 回滚
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		//REQUIRES
//		tianyaService.tianyaDaotest();//tianyaDaotest没有设置事务，默认REQUIRES，这时候会回滚
		/*try {
			//REQUIRES
			tianyaService.tianyaDaotest();//tianyaDaotest没有设置事务，默认REQUIRES，这时候也会回滚
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		//NESTED：外层事务失败时，会回滚内层事务所做的动作。而内层事务操作失败并不会引起外层事务的回滚
//		tianyaService.tianyaDaotest();//tianyaDaotest，外层会回滚
		/*try {
			//NESTED
			tianyaService.tianyaDaotest();////tianyaDaotest，内层失败，外层    不会    回滚
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		//NOT_SUPPORTED
//		tianyaService.tianyaDaotest();//tianyaDaotest，外层会回滚
		try {
			//NOT_SUPPORTED
			tianyaService.tianyaDaotest();////tianyaDaotest，内层失败，外层    不会    回滚
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

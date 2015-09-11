package com.novel.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.BookDao;
import com.novel.dao.UserBookDao;
import com.novel.entity.Book;
import com.novel.entity.UserBook;

@Service
public class MailService {
	Logger log = Logger.getLogger(MailService.class);
	@Autowired
	private BookDao bookDao;
	@Autowired
	private UserBookDao userBookDao;
	
	/**
	 * 非事务方式运行，读取已经commit的数据
	 * @param book
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,isolation=Isolation.READ_COMMITTED,readOnly=true)
	public void sendMessage() {
		try{
			List<UserBook> ubL = null;
			Book parm = new Book();
			parm.setSendMail(1);
			List<Book> bookL = bookDao.queryBooks(parm);//查询所有需要发送的章节信息
			for(Book book : bookL){
				ubL = userBookDao.queryUserBook(book);
				System.out.println("ubL = "+ubL);
			}
			log.info("SendMessage over.");
		}catch(Exception e){
			log.error(e,e);
		}
	}
	public BookDao getBookDao() {
		return bookDao;
	}
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
}

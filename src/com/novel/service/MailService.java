package com.novel.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.BookDao;
import com.novel.dao.BookInfoDao;
import com.novel.dao.UserBookDao;
import com.novel.entity.Book;
import com.novel.entity.BookInfo;
import com.novel.entity.UserBook;
import com.novel.util.SendMailUtils;

@Service
public class MailService {
	Logger log = Logger.getLogger(MailService.class);
	@Autowired
	private BookDao bookDao;
	@Autowired
	private UserBookDao userBookDao;
	@Autowired
	private BookInfoDao bookInfoDao;
	
	/**
	 * ������ʽ���У���ȡ�Ѿ�commit������
	 * @param book
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,isolation=Isolation.READ_COMMITTED,readOnly=true)
	public void sendMessage() {
		try{
			List<UserBook> ubL = null;
			List<BookInfo> biL = null;
			Book parm = new Book();
			parm.setSendMail(1);
			List<Book> bookL = bookDao.queryBooks(parm);//��ѯ������Ҫ���͵��½���Ϣ
//			��ѯ�����鼮��Ϣ
			biL = bookInfoDao.queryAllBookInfos();
			BookInfo tempBi = null;
			int index = 0;
			for(Book book : bookL){
				ubL = userBookDao.queryUserBook(book);
				System.out.println("book=" + book);
				System.out.println("ubL = "+ubL);
				//��ѯҪ���͵��Ȿ�鼮����Ϣ
				tempBi = new BookInfo();
				tempBi.setBookId(book.getBookId());
				index = biL.indexOf(tempBi);
				if(index > -1 && index < biL.size()){
					tempBi = biL.get(index);
				}
				System.out.println("bookInfo = " + tempBi);
				new SendMailUtils(book, tempBi, ubL).start();
				//������ɸ�״̬
				bookDao.updateSendState(book);
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

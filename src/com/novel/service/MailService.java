package com.novel.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.ChapterDao;
import com.novel.dao.BookInfoDao;
import com.novel.dao.UserBookDao;
import com.novel.entity.Chapter;
import com.novel.entity.BookInfo;
import com.novel.entity.UserBook;
import com.novel.util.SendMailUtils;

@Service
public class MailService {
	Logger log = Logger.getLogger(MailService.class);
	@Autowired
	private ChapterDao bookDao;
	@Autowired
	private UserBookDao userBookDao;
	@Autowired
	private BookInfoDao bookInfoDao;
	
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
			List<BookInfo> biL = null;
			Chapter parm = new Chapter();
			parm.setSendMail(1);
			List<Chapter> bookL = bookDao.queryChapters(parm);//查询所有需要发送的章节信息
//			查询所有书籍信息
			biL = bookInfoDao.queryAllBookInfos();
			BookInfo tempBi = null;
			int index = 0;
			for(Chapter book : bookL){
				ubL = userBookDao.queryUserBook(book);
				System.out.println("book=" + book);
				System.out.println("ubL = "+ubL);
				//查询要发送的这本书籍的信息
				tempBi = new BookInfo();
				tempBi.setBookId(book.getBookInfoId());
				index = biL.indexOf(tempBi);
				if(index > -1 && index < biL.size()){
					tempBi = biL.get(index);
				}
				System.out.println("bookInfo = " + tempBi);
//				new SendMailUtils(book, tempBi, ubL).start();
				//发送完成改状态
				bookDao.updateSendState(book);
			}
			log.info("SendMessage over.");
		}catch(Exception e){
			log.error(e,e);
		}
	}
	public ChapterDao getBookDao() {
		return bookDao;
	}
	public void setBookDao(ChapterDao bookDao) {
		this.bookDao = bookDao;
	}
}

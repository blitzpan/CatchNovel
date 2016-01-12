package com.novel.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.ChapterDao;
import com.novel.dao.SendMailDao;
import com.novel.entity.Chapter;
import com.novel.entity.ChapterCache;
import com.novel.entity.SendTask;

@Service
@Transactional
public class SendMailService {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private SendMailDao sendMailDao;
	@Autowired
	private ChapterDao chapterDao;
	
	public Object[] getOneTask() throws Exception{
		SendTask st = null;
		Chapter c = null;
		//获取未发送
		st = sendMailDao.getOneTask();
		c = (Chapter) ChapterCache.get(st.getChapterId());
		if(c == null){
			c = chapterDao.getOneChapterById(st.getChapterId());
		}
		//设置为已发送成功
		sendMailDao.setSended(st);
		Object[] arr = new Object[]{st, c};
		return arr;
	}

	public SendMailDao getSendMailDao() {
		return sendMailDao;
	}

	public void setSendMailDao(SendMailDao sendMailDao) {
		this.sendMailDao = sendMailDao;
	}

	public ChapterDao getChapterDao() {
		return chapterDao;
	}

	public void setChapterDao(ChapterDao chapterDao) {
		this.chapterDao = chapterDao;
	}
}
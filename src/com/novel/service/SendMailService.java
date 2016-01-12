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
	private Thread rt = null;
	private boolean isRunning = true;
	private long sleepTime = 1000 * 20;//20s发送一次
	
	@Autowired
	private SendMailDao sendMailDao;
	@Autowired
	private ChapterDao chapterDao;
	
	public SendMailService(){
		
	}
	
	public void start() throws Exception{
		if(rt == null || !rt.isAlive()){
			log.debug("SendMailService.start()");
			rt = new Thread(new SendMailThread());
			rt.start();
		}
	}
	public void stop() {
		isRunning = false;
	}
	class SendMailThread implements Runnable {
		public void run() {
			log.info("SendMailThread run.");
			SendTask st = null;
			Chapter c = null;
			while (isRunning) {
				try {
					//获取未发送
					st = sendMailDao.getOneTask();
					c = (Chapter) ChapterCache.get(st.getChapterId());
					if(c == null){
						c = chapterDao.getOneChapterById(st.getChapterId());
					}
					//设置为已发送成功
					sendMailDao.setSended(st);
					if(c!=null){//发送
						
					}
				} catch (Exception e) {
					log.error("SendMailThread error.", e);
				}
				try{
					Thread.sleep(sleepTime);
				}catch(Exception e){
					log.error("sleep error.", e);
				}
			}
			log.info("SendMailThread end.");
		}
	}
}
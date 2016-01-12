package com.novel.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.SendMailDao;
import com.novel.entity.SendTask;

@Service
@Transactional
public class SendTaskService {
	private Logger log = Logger.getLogger(this.getClass());
	private Thread rt = null;
	private boolean isRunning = true;
	private long sleepTime = 1000 * 60*5;
	
	@Autowired
	private SendMailDao sendMailDao;
	
	public SendTaskService(){
		
	}
	
	public void start() throws Exception{
		if(rt == null || !rt.isAlive()){
			log.debug("SendTaskService.start()");
			rt = new Thread(new SendTaskThread());
			rt.start();
		}
	}
	public void stop() {
		isRunning = false;
	}
	class SendTaskThread implements Runnable {
		public void run() {
			log.info("SendTaskThread run.");
			while (isRunning) {
				try {
					List<SendTask> stl = sendMailDao.queryAllNeedSend();
					sendMailDao.batchAddTask(stl);
				} catch (Exception e) {
					log.error("SendTaskThread error.", e);
				}
				try{
					Thread.sleep(sleepTime);
				}catch(Exception e){
					log.error("sleep error.", e);
				}
			}
			log.info("SendTaskThread end.");
		}
	}
}
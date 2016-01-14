package com.novel.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.novel.entity.Chapter;
import com.novel.entity.SendTask;
import com.novel.service.SendMailService;

@Component
public class SendMailThread implements Runnable {
	private Logger log = Logger.getLogger(this.getClass());
	private boolean isRunning = true;
	private long sleepTime = 1000 * 20;// 20s发送一次
	@Autowired
	private SendMailService sendMailService;
	@Autowired
	private SendMailUtils sendMailUtils;

	public void run() {
		log.info("SendMailThread run.");
		SendTask st = null;
		Chapter c = null;
		while (isRunning) {
			try {
				Object[] arr = sendMailService.getOneTask();
				if(arr!=null){
					st = (SendTask) arr[0];
					c = (Chapter) arr[1];
					SendMailUtils smu = new SendMailUtils(c,st);
					log.info("发送邮件to=" + st.getEmail() + "章节=" + c.getId());
					log.debug("发送邮件内容=" + c);
					smu.start();
				}
			} catch (Exception e) {
				log.error("SendMailThread error.", e);
			}
			try {
				Thread.sleep(sleepTime);
			} catch (Exception e) {
				log.error("sleep error.", e);
			}
		}
		log.info("SendMailThread end.");
	}

	public SendMailService getSendMailService() {
		return sendMailService;
	}

	public void setSendMailService(SendMailService sendMailService) {
		this.sendMailService = sendMailService;
	}

	public SendMailUtils getSendMailUtils() {
		return sendMailUtils;
	}

	public void setSendMailUtils(SendMailUtils sendMailUtils) {
		this.sendMailUtils = sendMailUtils;
	}
}
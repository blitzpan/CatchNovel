package com.novel.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novel.service.SendTaskService;

@Component
public class SendTaskThread implements Runnable {
	private Logger log = Logger.getLogger(this.getClass());
	private boolean isRunning = true;
	private long sleepTime = 1000 * 60 * 5;
	
	@Autowired
	private SendTaskService sendTaskService;

	public void run() {
		log.info("SendTaskThread run.");
		while (isRunning) {
			try {
				sendTaskService.doSendTask();
			} catch (Exception e) {
				log.error("SendTaskThread error.", e);
			}
			try {
				Thread.sleep(sleepTime);
			} catch (Exception e) {
				log.error("sleep error.", e);
			}
		}
		log.info("SendTaskThread end.");
	}
}

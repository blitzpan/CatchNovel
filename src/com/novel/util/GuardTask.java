package com.novel.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class GuardTask {
	Logger log = Logger.getLogger(this.getClass());
	Thread sendTaskThread;
	Thread sendMailThread;
	
	public void doGuard() {
		//发送任务
		/*
		try{
			if(sendTaskThread==null || !sendTaskThread.isAlive()){
				sendTaskThread = new Thread((SendTaskThread)SpringContextUtil.getBean("sendTaskThread"));
				sendTaskThread.start();
			}
		}catch(Exception e){
			log.error("sendTaskThread error.", e);
		}
		*/

		//发送email
		try{
			if(sendMailThread==null || !sendMailThread.isAlive()){
				sendMailThread = new Thread((SendMailThread)SpringContextUtil.getBean("sendMailThread"));
				sendMailThread.start();
			}
		}catch(Exception e){
			log.error("sendMailThread error.", e);
		}
		
	}
}
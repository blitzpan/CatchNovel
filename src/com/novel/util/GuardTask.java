package com.novel.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class GuardTask {
	Logger log = Logger.getLogger(this.getClass());
	Thread sendTaskThread;
	
	public void doGuard() {
		try{
			if(sendTaskThread==null || !sendTaskThread.isAlive()){
				sendTaskThread = new Thread((SendTaskThread)SpringContextUtil.getBean("sendTaskThread"));
				sendTaskThread.start();
			}
		}catch(Exception e){
			log.error("sendTaskThread error.", e);
		}
		
	}
}
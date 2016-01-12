package com.novel.util;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.novel.service.MailService;
import com.novel.service.SendTaskService;
@Component
@Scope("prototype")  //每一个请求都有一个类来处理，避免线程安全问题。
public class SendMailTask {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private MailService mailService;
	@Autowired
	private SendTaskService sendTaskService;
	
	public void doSendTask() {
		try{
			sendTaskService.start();
		}catch(Exception e){
			log.error("doSendTask error.", e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 第一次发送邮件
	 */
	public void sendMail() {
		System.out.println("第一次发送邮件：" + new Date(System.currentTimeMillis()).toLocaleString());
		mailService.sendMessage();
    }
	/**
	 * 发送失败再次发送邮件
	 */
	public void reSendMail(){
		System.out.println("再次发送邮件：" + new Date(System.currentTimeMillis()).toLocaleString());
	}
	public MailService getMailService() {
		return mailService;
	}
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	public SendTaskService getSendTaskService() {
		return sendTaskService;
	}
	public void setSendTaskService(SendTaskService sendTaskService) {
		this.sendTaskService = sendTaskService;
	}

}

package com.novel.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.novel.service.MailService;
@Component
@Scope("prototype")  //每一个请求都有一个类来处理，避免线程安全问题。
public class SendMailTask {
	@Autowired
	private MailService mailService;
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

}

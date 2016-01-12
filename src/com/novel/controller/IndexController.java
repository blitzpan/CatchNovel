package com.novel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.novel.catcher.TianyaCatcher;
import com.novel.service.ChapterService;
import com.novel.service.MailService;
import com.novel.service.TianyaService;
import com.novel.service.TransactionalService;
import com.novel.util.SendMailUtils;

@Controller
public class IndexController {
	@Autowired
	private TianyaCatcher tianyaCatcher;
	@Autowired
	private TransactionalService transactionalService;
	@Autowired
	private SendMailUtils mailUtils;
	@Autowired
	private ChapterService bookService;
	@Autowired
	private TianyaService tianyaService;
	@Autowired
	private MailService mailService;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		try{
			tianyaCatcher.queryAllTask();
//			transactionalService.addBooks();
		}catch(Exception e){
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "hello world!");
		mv.setViewName("index");
		return mv;
	}
	@RequestMapping("/sendMail")
	public void sendMail(){
		try{
//			mailUtils.sendSimpleMail();
//			mailUtils.sendHtmlMail();
//			mailUtils.sendTemplateMail();
			mailService.sendMessage();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@RequestMapping("/test")
	public void test() {
		try {
			long begin = System.currentTimeMillis();
			System.out.println("begin:");
			//在Controller中的两个service操作数据库是两个事务，tianyaService中的异常并不会导致bookService的回滚
//			bookService.test();
			System.out.println("end.spend time=" + (System.currentTimeMillis() - begin)/1000);
//			tianyaService.test();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TianyaCatcher getTianyaCatcher() {
		return tianyaCatcher;
	}

	public void setTianyaCatcher(TianyaCatcher tianyaCatcher) {
		this.tianyaCatcher = tianyaCatcher;
	}

	public TransactionalService getTransactionalService() {
		return transactionalService;
	}

	public void setTransactionalService(TransactionalService transactionalService) {
		this.transactionalService = transactionalService;
	}
	public SendMailUtils getMailUtils() {
		return mailUtils;
	}
	public void setMailUtils(SendMailUtils mailUtils) {
		this.mailUtils = mailUtils;
	}
	public MailService getMailService() {
		return mailService;
	}
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}


}

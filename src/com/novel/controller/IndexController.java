package com.novel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.novel.catcher.TianyaCatcher;
import com.novel.service.TransactionalService;

@Controller
public class IndexController {
	@Autowired
	private TianyaCatcher tianyaCatcher;
	@Autowired
	private TransactionalService transactionalService;
	
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


}

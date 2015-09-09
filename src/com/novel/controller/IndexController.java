package com.novel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.novel.catcher.TianyaCatcher;

@Controller
public class IndexController {
	@Autowired
	private TianyaCatcher tianyaCatcher;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		try{
			tianyaCatcher.queryAllTask();
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


}

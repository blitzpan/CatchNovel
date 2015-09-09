package com.novel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		System.out.println("开始连接数据库：");
		String sql = "SELECT count(*) from book";
		int c = jdbcTemplate.queryForInt(sql);
		System.out.println("结果="+c);
		
		
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "hello world!");
		mv.setViewName("index");
		return mv;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}

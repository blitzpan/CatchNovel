package com.novel.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.novel.catcher.TianyaCatcher;
/**
 *定时抓取天涯论坛 
 */
@Component
@Scope("prototype")  //每一个请求都有一个类来处理，避免线程安全问题。
public class CatchTianyaTask {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private TianyaCatcher tianyaCatcher;
	
	public void job1() {
		log.info("CatchTianyaTask begin:");
		tianyaCatcher.initThreads();
		log.info("CatchTianyaTask end.");
	}
	
	public TianyaCatcher getTianyaCatcher() {
		return tianyaCatcher;
	}
	public void setTianyaCatcher(TianyaCatcher tianyaCatcher) {
		this.tianyaCatcher = tianyaCatcher;
	}
}
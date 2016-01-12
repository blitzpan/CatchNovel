package com.novel.util;

import java.util.Date;

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
	@Autowired
	private TianyaCatcher tianyaCatcher;
	public void job1() {
		System.out.println("采集任务开始：" + new Date());  
		tianyaCatcher.initThreads();
        System.out.println("采集任务结束：" + new Date());  
	}
	
	public TianyaCatcher getTianyaCatcher() {
		return tianyaCatcher;
	}
	public void setTianyaCatcher(TianyaCatcher tianyaCatcher) {
		this.tianyaCatcher = tianyaCatcher;
	}
}
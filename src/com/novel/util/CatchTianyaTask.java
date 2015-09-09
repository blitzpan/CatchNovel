package com.novel.util;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 *定时抓取天涯论坛 
 */
@Component
@Scope("prototype")  //每一个请求都有一个类来处理，避免线程安全问题。
public class CatchTianyaTask {
	public void job1() {  
        System.out.println("任务进行中。。。" + new Date(System.currentTimeMillis()).toLocaleString());  
    }

}

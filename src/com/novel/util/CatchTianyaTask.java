package com.novel.util;

import java.util.Date;

import org.springframework.stereotype.Component;
/**
 *定时抓取天涯论坛 
 */
@Component
public class CatchTianyaTask {
	public void job1() {  
        System.out.println("任务进行中。。。" + new Date(System.currentTimeMillis()).toLocaleString());  
    }  
}

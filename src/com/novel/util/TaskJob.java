package com.novel.util;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TaskJob {
	public void job1() {  
        System.out.println("任务进行中。。。" + new Date(System.currentTimeMillis()).toLocaleString());  
    }  
}

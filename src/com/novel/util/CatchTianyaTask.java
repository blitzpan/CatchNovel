package com.novel.util;

import java.util.Date;

import org.springframework.stereotype.Component;
/**
 *��ʱץȡ������̳ 
 */
@Component
public class CatchTianyaTask {
	public void job1() {  
        System.out.println("��������С�����" + new Date(System.currentTimeMillis()).toLocaleString());  
    }  
}

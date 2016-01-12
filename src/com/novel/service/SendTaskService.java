package com.novel.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.SendMailDao;
import com.novel.entity.SendTask;

@Service
@Transactional
public class SendTaskService {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private SendMailDao sendMailDao;
	
	public void doSendTask() throws Exception{
		List<SendTask> stl = sendMailDao.queryAllNeedSend();
		sendMailDao.batchAddTask(stl);
	}	
}
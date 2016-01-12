package com.novel.entity;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

public class TianyaQueue {
	static Logger log = Logger.getLogger(TianyaQueue.class);
	
	private static Queue tyQ = new LinkedBlockingQueue();
	
	public static void add(Tianya ty){
		try{
			if(!tyQ.contains(ty)){
				tyQ.add(ty);
			}
		}catch(Exception e){
			log.error("TianyaQueue.add", e);
		}
	}
	public static Tianya poll(){
		Tianya ty = null;
		try{
			ty = (Tianya) tyQ.poll();
		}catch(Exception e){
			log.error("TianyaQueue.poll", e);
		}
		return ty;
	}
}

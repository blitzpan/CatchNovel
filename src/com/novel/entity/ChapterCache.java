package com.novel.entity;

import java.util.Map;
import java.util.WeakHashMap;

public class ChapterCache {
	private static Map chapters = new WeakHashMap();
	
	public static void put(Object key, Object value) {
		chapters.put(key, value);
	}
	public static Object get(Object key){
//		return chapters.get(key);
		return null;//这个地方逻辑有问题
	}
}

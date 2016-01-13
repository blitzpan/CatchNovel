package com.novel.catcher;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novel.entity.Chapter;
import com.novel.entity.Tianya;
import com.novel.entity.TianyaQueue;
import com.novel.service.ChapterService;
import com.novel.service.TianyaService;
@Component
public class TianyaCatcher {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private TianyaService tianyaService;
	@Autowired 
	private ChapterService chapterService;
	
	private final int threadCount = 1;
	private Thread[] threads = new Thread[threadCount];
	
	public void initThreads() {
		try{
			//初始化基本数据
			tianyaService.addTianyaQueue();
			log.info("TianyaQueue.size=" + TianyaQueue.size());
			
			//开启线程
			CatchOne co;
			for(int i=0; i<threadCount; i++){
				if(threads[i] == null || !threads[i].isAlive()){
					threads[i] = new Thread(new CatchOne());
					threads[i].start();
				}
			}
		}catch(Exception e){
			log.error("initThreads error.", e);
		}
	}
	
	class CatchOne implements Runnable{
		int sleepTime = 3000;
		public void run() {
			log.info("采集线程run:");
			while(true){
				Document doc = null;//一个页面
				String oldPageNum = "";//初始化的pageNum
				String pageNum = "";//页码
				String content = "";
				Tianya ty = TianyaQueue.poll();
				if(ty == null){
					break;
				}
				String url = "";
				pageNum = ty.getPageNum();
				int i = 0;
				Chapter chapter = null;
				while(true){
					try{
						chapter = new Chapter();
						url = ty.getRealUrl();
						//第一次获取
						doc = getDocByUrl(url);
						pageNum = getPageNum(doc);
						if(pageNum.equals(oldPageNum)){//已经抓取过了，不处理
							break;
						}
						content = getContent(ty,doc);
						log.debug("抓取到第【"+pageNum+"】页的内容=\n" + content);
						//抓取的内容入库
						chapter.setPageNum(pageNum);
						chapter.setBookInfoId(ty.getBookId());
						chapter.setContent(content);
						chapter.setUrl(url);
						tianyaService.addChapter(ty, chapter);
						
						oldPageNum = pageNum;
						ty.pageNumAdd();
						pageNum = ty.getPageNum();
						if(i++ == 3){//测试，3条就跳出循环
							log.debug("超过了三次，break。");
							break;
						}
					}catch(Exception e){
						log.error("抓去一页内容出现异常。", e);
					}
					try{
						Thread.sleep(sleepTime);
					}catch(Exception e){
						log.error("sleep error.", e);
					}
				}
			}
			log.info("采集线程运行结束！");
		}
	}

	public Document getDocByUrl(String url) throws Exception{
		Document doc = null;
		try{
			doc = Jsoup.connect(url).timeout(1000*60).get();
//			this.writeToFile(doc.toString());
		}catch(Exception e){
			log.error("getDocByUrl", e);
			throw e;
		}
		return doc;
	}

	private String getContent(Tianya ty, Document doc) throws Exception{
		StringBuffer sb = new StringBuffer();//本页的所有内容
		Elements mainElements = null;
		Elements atlItems = doc.getElementsByClass("atl-item");
		if(ty.getPageNum().equals("1")){//如果是首页，那么1楼单独处理
			mainElements = doc.getElementsByClass("atl-main");
		}
		Element mainE = null;
		for(int i=0; mainElements!=null && i<mainElements.size(); i++){
			mainE = mainElements.get(i);
			sb.append(mainE.text());
		}
		for(Element e: atlItems){
			Elements altInfos = e.getElementsByClass("atl-info");
			if(altInfos.size()>0 && altInfos.get(0).toString().contains(ty.getAuthorId())){
				sb.append(e.getElementsByClass("bbs-content").text());
			}
		}
		return sb.toString();
	}
	private String getPageNum(Document doc) throws Exception{
//		doc.baseUri()、doc.location()获取重定向之后的页面地址。
//		如100000页返回的是：http://bbs.tianya.cn/post-16-1150797-132.shtml
		String pageNum = "";
		String redirectUrl = doc.baseUri();
		Pattern p = Pattern.compile("\\d+\\.shtml");
		Matcher m = p.matcher(redirectUrl);
		if(m.find()){
			pageNum = m.group(0).replace(".shtml", "");
		}
		return pageNum;
	}
	public static void writeToFile(String content) {
		try {
			File file = new File("d:\\tianya.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(content.toString().getBytes("utf-8"));
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public TianyaService getTianyaService() {
		return tianyaService;
	}

	public void setTianyaService(TianyaService tianyaService) {
		this.tianyaService = tianyaService;
	}


	public static void main(String[] args) {
		String url = "http://bbs.tianya.cn/post-16-1150797-3.shtml";
		System.out.println(url + "的内容：");
		TianyaCatcher t = new TianyaCatcher();
		try {
			t.getDocByUrl(url);
//			t.getContent();
//			t.getPageNum();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ChapterService getChapterService() {
		return chapterService;
	}

	public void setChapterService(ChapterService chapterService) {
		this.chapterService = chapterService;
	}
	
}

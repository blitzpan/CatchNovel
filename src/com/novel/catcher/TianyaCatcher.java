package com.novel.catcher;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novel.dao.BookDao;
import com.novel.dao.TianyaDao;
import com.novel.entity.Tianya;
import com.novel.util.ComUtils;
@Service
public class TianyaCatcher {
	@Autowired
	private TianyaDao tianyaDao;
	@Autowired 
	private BookDao bookDao;
	private List<Tianya> tyL = null;//天涯任务list
	/**
	 * 查询所有要采集的任务
	 * @throws Exception
	 */
	public void queryAllTask() throws Exception{
		tyL = tianyaDao.queryAll(null);
		System.out.println("查询所有任务开始：");
		for(Tianya ty : tyL){
			System.out.println("采集开始：" + ty);
			CatchOne co = new CatchOne(ty);
			new Thread(co).start();
		}
		System.out.println("查询所有任务结束。");
	}
	class CatchOne implements Runnable{
		Tianya ty = null;
		public CatchOne(Tianya ty) {
			super();
			this.ty = ty;
		}
		public void run() {
			catchOne(ty);
		}
	}

	/**
	 * 采集一个
	 * @param ty
	 */
	private void catchOne(Tianya ty){
		Document doc = null;//一个页面
		String oldPageNum = "";//初始化的pageNum
		String pageNum = "";//页码
		String content = "";
		try{
			String url = "";
			pageNum = ty.getPageNum();
			int i = 0;
			do{
				url = ty.getArticleUrl().replace("pageNum", pageNum);
				//第一次获取
				doc = getDocByUrl(url);
				pageNum = getPageNum(doc);
				if(pageNum.equals(oldPageNum)){//已经抓取过了，不处理
					break;
				}
				content = getContent(ty,doc);
				System.out.println("抓取到第【"+pageNum+"】页的内容=\n" + content);
				oldPageNum = pageNum;
				pageNum = "" + (ComUtils.parseInt(pageNum) + 1);//+1，查询下一页
				if(i++ == 3){
					break;
				}
			}while(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public Document getDocByUrl(String url) throws Exception{
		Document doc = null;
		try{
			doc = Jsoup.connect(url).get();
//			this.writeToFile(doc.toString());
			System.out.println("完成");
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return doc;
	}

	private String getContent(Tianya ty, Document doc) throws Exception{
		StringBuffer sb = new StringBuffer();//本页的所有内容
		Elements atlItems = doc.getElementsByClass("atl-item"); 
		for(Element e: atlItems){
			Elements altInfos = e.getElementsByClass("atl-info");
			if(altInfos.size()>0 && altInfos.get(0).toString().contains(ty.getAuthorId())){
				sb.append(e.getElementsByClass("bbs-content").text());
//				System.out.println(e.getElementsByClass("bbs-content").text());
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
	
	public TianyaDao getTianyaDao() {
		return tianyaDao;
	}
	public void setTianyaDao(TianyaDao tianyaDao) {
		this.tianyaDao = tianyaDao;
	}
	public BookDao getBookDao() {
		return bookDao;
	}
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
}

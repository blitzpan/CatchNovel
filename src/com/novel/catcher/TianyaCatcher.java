package com.novel.catcher;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novel.entity.Book;
import com.novel.entity.Tianya;
import com.novel.service.BookService;
import com.novel.service.TianyaService;
@Service
public class TianyaCatcher {
	@Autowired
	private TianyaService tianyaService;
	@Autowired 
	private BookService bookService;
	private final int threadCount = 5;
	private ExecutorService pool = null;
	
	private List<Tianya> tyL = null;//��������list
	/**
	 * ��ѯ����Ҫ�ɼ�������
	 * @throws Exception
	 */
	public void queryAllTask() {
		try{
			tyL = tianyaService.queryAll(null);
			System.out.println("��ѯ��������ʼ��");
			pool = Executors.newFixedThreadPool(threadCount);
			for(Tianya ty : tyL){
				System.out.println("�ɼ���ʼ��" + ty);
				CatchOne co = new CatchOne(ty);
				pool.execute(co);//���ӵ��̳߳���ȥִ��
//				new Thread(co).start();
			}
			pool.shutdown();
			System.out.println("��ѯ�������������");
		}catch(Exception e){
			e.printStackTrace();
		}
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
	 * �ɼ�һ��
	 * @param ty
	 */
	private void catchOne(Tianya ty){
		Document doc = null;//һ��ҳ��
		String oldPageNum = "";//��ʼ����pageNum
		String pageNum = "";//ҳ��
		String content = "";
		try{
			String url = "";
			pageNum = ty.getPageNum();
			int i = 0;
			Book book = null;
			do{
				book = new Book();
				url = ty.getRealUrl();
				//��һ�λ�ȡ
				doc = getDocByUrl(url);
				pageNum = getPageNum(doc);
				if(pageNum.equals(oldPageNum)){//�Ѿ�ץȡ���ˣ�������
					break;
				}
				content = getContent(ty,doc);
				System.out.println("ץȡ���ڡ�"+pageNum+"��ҳ������=\n" + content);
				//ץȡ���������
				book.setPageNum(pageNum);
				book.setBookId(ty.getBookId());
				book.setContent(content);
				book.setUrl(url);
				tianyaService.addBook(ty, book);
				
				oldPageNum = pageNum;
				ty.pageNumAdd();
				pageNum = ty.getPageNum();
				if(i++ == 3){//���ԣ�3��������ѭ��
					System.out.println("���������Σ�break��");
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
			doc = Jsoup.connect(url).timeout(1000*60).get();
//			this.writeToFile(doc.toString());
			System.out.println("���");
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return doc;
	}

	private String getContent(Tianya ty, Document doc) throws Exception{
		StringBuffer sb = new StringBuffer();//��ҳ����������
		Elements mainElements = null;
		Elements atlItems = doc.getElementsByClass("atl-item");
		if(ty.getPageNum().equals("1")){//�������ҳ����ô1¥��������
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
//				System.out.println(e.getElementsByClass("bbs-content").text());
			}
		}
		return sb.toString();
	}
	private String getPageNum(Document doc) throws Exception{
//		doc.baseUri()��doc.location()��ȡ�ض���֮���ҳ���ַ��
//		��100000ҳ���ص��ǣ�http://bbs.tianya.cn/post-16-1150797-132.shtml
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

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public static void main(String[] args) {
		String url = "http://bbs.tianya.cn/post-16-1150797-3.shtml";
		System.out.println(url + "�����ݣ�");
		TianyaCatcher t = new TianyaCatcher();
		try {
			t.getDocByUrl(url);
//			t.getContent();
//			t.getPageNum();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

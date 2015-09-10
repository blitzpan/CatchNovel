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
	private List<Tianya> tyL = null;//��������list
	/**
	 * ��ѯ����Ҫ�ɼ�������
	 * @throws Exception
	 */
	public void queryAllTask() throws Exception{
		tyL = tianyaDao.queryAll(null);
		System.out.println("��ѯ��������ʼ��");
		for(Tianya ty : tyL){
			System.out.println("�ɼ���ʼ��" + ty);
			CatchOne co = new CatchOne(ty);
			new Thread(co).start();
		}
		System.out.println("��ѯ�������������");
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
			do{
				url = ty.getArticleUrl().replace("pageNum", pageNum);
				//��һ�λ�ȡ
				doc = getDocByUrl(url);
				pageNum = getPageNum(doc);
				if(pageNum.equals(oldPageNum)){//�Ѿ�ץȡ���ˣ�������
					break;
				}
				content = getContent(ty,doc);
				System.out.println("ץȡ���ڡ�"+pageNum+"��ҳ������=\n" + content);
				oldPageNum = pageNum;
				pageNum = "" + (ComUtils.parseInt(pageNum) + 1);//+1����ѯ��һҳ
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
			System.out.println("���");
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return doc;
	}

	private String getContent(Tianya ty, Document doc) throws Exception{
		StringBuffer sb = new StringBuffer();//��ҳ����������
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

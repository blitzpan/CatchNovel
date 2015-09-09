package com.novel.tianya;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TianyaCatcher {
	Document doc = null;//一个页面
	String pageNum = "";//页码
	StringBuffer sb = new StringBuffer();//本页的所有内容
	public static void main(String[] args) {
		String url = "http://bbs.tianya.cn/post-16-1150797-3.shtml";
		System.out.println(url + "的内容：");
		TianyaCatcher t = new TianyaCatcher();
		
		try {
			t.getDocByUrl(url);
			t.getContent();
//			t.getPageNum();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getDocByUrl(String url) throws Exception{
		try{
			doc = Jsoup.connect(url).get();
			this.writeToFile(doc.toString());
			System.out.println("完成");
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
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
	private void getContent() throws Exception{
		Elements atlItems = doc.getElementsByClass("atl-item"); 
//				doc.select("div.atl-item");
//		System.out.println(atlItems);
		for(Element e: atlItems){
			Elements altInfos = e.getElementsByClass("atl-info");
			if(altInfos.size()>0 && altInfos.get(0).toString().contains("御风楼主人")){
				System.out.println(e.getElementsByClass("bbs-content").text());
			}
		}
	}
	private void getPageNum() throws Exception{
//		doc.baseUri()、doc.location()获取重定向之后的页面地址。
//		如100000页返回的是：http://bbs.tianya.cn/post-16-1150797-132.shtml
		String redirectUrl = doc.baseUri();
		Pattern p = Pattern.compile("\\d+\\.shtml");
		Matcher m = p.matcher(redirectUrl);
		if(m.find()){
			pageNum = m.group(0).replace(".shtml", "");
		}
	}
	
	
	/*
	public static String getContent(String url) {
		String res = null;
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			response = httpclient.execute(httpGet);
			HttpEntity entity1 = response.getEntity();
			res = EntityUtils.toString(entity1);
			System.out.println(res);
			// writeToFile(res);
		} catch (Exception e) {
			System.out.println("UrlGet error.");
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	public static void parseHtml(String url) {
		try {
			Parser parser = new Parser(url);
			parser.setEncoding("utf-8");
			NodeFilter filter = new AndFilter(
					new NodeFilter[] {new NodeClassFilter(Div.class), new HasAttributeFilter("class","atl-item") });
			NodeList list = parser.parse(filter);
			for (int i = 0; i < list.size(); i++) {
				Node n = list.elementAt(i);
				NodeList nl = n.getChildren();
				if (nl.elementAt(1).toHtml().contains("<span><strong class=\"host\">楼主")) {
					Node n2 = nl.elementAt(3).getChildren().elementAt(3).getChildren().elementAt(1);
					String tempStr = n2.toHtml().replaceAll("<div class=\"bbs-content\">", "").replaceAll("</div>", "").trim();
					tempStr = tempStr.replaceAll("<a\\s.*?href=\"([^\"]+)\"[^>]*>(.*?)</a>", "");
					System.out.println(tempStr);
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	*/
}

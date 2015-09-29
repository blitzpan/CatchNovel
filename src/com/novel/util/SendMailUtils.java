package com.novel.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.novel.entity.Book;
import com.novel.entity.BookInfo;
import com.novel.entity.UserBook;

import freemarker.template.Template;
import freemarker.template.TemplateException;
@Component
public class SendMailUtils extends Thread{
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private FreeMarkerConfigurer freemarkerConfiguration;
	private Book book;
	private BookInfo bookInfo;
	private List<UserBook> ubL = null;
	
	
	public SendMailUtils() {
		super();
	}
	public SendMailUtils(Book book, BookInfo bi, List<UserBook> ubL) {
		super();
		this.book = book;
		this.ubL = ubL;
		this.bookInfo = bi;
	}
	@Override
	public void run() {
		try {
			this.sendTemplateMail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * �������ı��ʼ�
	 * @return
	 */
	public String sendSimpleMail() {
		SimpleMailMessage mail = new SimpleMailMessage();
		try {
			mail.setTo("1028353676@qq.com");// ������
			mail.setFrom("youxiangformajia@163.com");// ������,��xml�е�һ��
			mail.setSubject("�ʼ�����");// ����
			mail.setText("���ʼ����Ͳ���");// �ʼ�����
			mailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "a";
	}
	/**
	 * html�ʼ�
	 * @throws Exception
	 */
	public void sendHtmlMail() throws Exception{
		// �����ʼ���Ϣ,���ͼ��ʼ���html�ʼ�������  
        MimeMessage mailMessage = mailSender.createMimeMessage();  
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true,"GBK");  
        
        messageHelper.setTo("1028353676@qq.com");// ������
        messageHelper.setFrom("youxiangformajia@163.com");// ������,��xml�е�һ��
        messageHelper.setSubject("�ʼ�����");// ����  
        
        // true ��ʾ����HTML��ʽ���ʼ�  
        messageHelper  
                .setText(  
                        "<html><head>	<title>html�ʼ�����</title></head><body>	<center><h1>html�ʼ�����</h1></center>	<div id='content'>		<p style='text-indent: 2em;'>			����Ԭ�������Ԭ�����ǰ��ջ���ʦ��ѧԺ�����ѧԺ�Ĵ���ѧ����9��8������8�����ң�����У�ſڷ�����һλˤ������̫������������ؾ͸�������120����������˼���Ҫ��ȫ��		</p>		<p style='text-indent: 2em;'>������Ů��ѧ���Ʒ���̫�����Ŀ�����Ѿ��������Ͼ���˵�������ͨ������΢���׶�����ṫ������������һĻ���������ֳ���Ƭ����Ŀ����˵����ʱ��������ȥ����ʦ��ѧԺ�Է������СԬ������С����鷢��֮ǰ��������������·���ȣ�һ��һ�˵ġ�������СԬ�ﳵ����̫��߾���������֪��ʲôԭ����̫�͵����ˡ���ʱСԬ�ﳵ�Ѿ���ȥ�ˣ���̫�ֺ�סСԬ��СԬ�ѳ���ͣ�£�����̫������������������ʱ��ԥ�ǲ��ǹ�ȥ���������ǹ�ȥ�ˣ���СԬһ����������ȡ���Ŀ������ȷ˵��СԬ�ﳵ����̫��߾���ʱ������֮�����о���ģ��Ҳ�����ײ�����ˣ�����û�п������������в���֮��ģ����˱�̧��120���ȳ�ǰ��һֱ�����ڵ��ϵġ�</br>		</p>	</div>	<div id='adDiv'>		<img src='http://www.p5w.net/money/xfsh/201509/W020150911358035928505.png' width='300' height='300'/>		<a href='http://www.p5w.net/money/xfsh/201509/t20150911_1191590.htm' target='_blank'>�鿴ԭ��</a>	</div></body></html>",  
                        true);  
        // �����ʼ�  
        mailSender.send(mailMessage);  
  
        System.out.println("�ʼ����ͳɹ�..");  
	}
	/**
	 * ͨ��ģ���������ʼ�
	 * @return
	 */
	public void sendTemplateMail() throws Exception{
		if(mailSender == null){
			//�����ټ���springContext.xml�ļ�,��Ϊ��web.xml��������,�ڳ����������Ǿ����ˡ�
			mailSender = (JavaMailSender) SpringContextUtil.getBean("mailSender");  
		}
		// �����ʼ���Ϣ,���ͼ��ʼ���html�ʼ�������  
        MimeMessage mailMessage = mailSender.createMimeMessage();  
        //����һ��Ҫ��gbk�������utf-8�Ļ����������������
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true,"gbk");
/*
//		Ⱥ���ʼ�
		String[] toUsers = new String[ubL.size()];
        int i=0;
        for(UserBook ub: ubL){
        	toUsers[i++] = ub.getEmail(); 
        }
        messageHelper.setTo(toUsers);//������
*/
        messageHelper.setFrom("youxiangformajia@163.com");// ������,��xml�е�һ��
        messageHelper.setSubject(bookInfo.getArticleName());// ����  
        
        // true ��ʾ����HTML��ʽ���ʼ�  
        messageHelper.setText(getEmailContent(), true);
//        mailSender.send(mailMessage);//Ⱥ���ʼ�
        
//      һ�����ķ��ʼ�
        for(UserBook ub: ubL){
        	messageHelper.setTo(ub.getEmail());
        	mailSender.send(mailMessage);
        }
        System.out.println("�ʼ����ͳɹ�..");  
	}
	private String getEmailContent() throws Exception{
		try {
			if(freemarkerConfiguration == null){
				freemarkerConfiguration = (FreeMarkerConfigurer) SpringContextUtil.getBean("freemarkerConfiguration");  
			}
			Template template = freemarkerConfiguration.getConfiguration().getTemplate("mail.ftl");

			Map<String, String> map = new HashMap<String, String>();
			
			map.put("homeUrl", bookInfo.getHomeUrl());
			map.put("title", bookInfo.getArticleName());
			map.put("authorName", bookInfo.getAuthorName());
			map.put("sendTime", book.getGatherDate());
			map.put("content", book.getContent());
			map.put("url", book.getUrl());
			map.put("fromName", "<a href=\""+bookInfo.getHomeUrl()+"\" target=\"_blank\">�����ҳ</a>");
			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
			return content;

		} catch (TemplateException e) {
			System.out.println("Error while processing FreeMarker template ");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("Error while open template file ");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error while generate Email Content ");
			e.printStackTrace();
		}
		return "";
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public FreeMarkerConfigurer getFreemarkerConfiguration() {
		return freemarkerConfiguration;
	}
	public void setFreemarkerConfiguration(FreeMarkerConfigurer freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;
	}
}

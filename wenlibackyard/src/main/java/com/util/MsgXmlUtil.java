package com.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.bean.req.MsgRequest;
import com.bean.resp.Article;
import com.bean.resp.MsgResponseNews;
import com.bean.resp.MsgResponseText;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * ���������ݺͷ������ݽ��д�����
 * 
 * @author Runaway
 *
 */
@SuppressWarnings("unchecked")
public class MsgXmlUtil {
	/**  
	 *  ������Ϣ���ͣ��ı� 
	 *
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text"; 
	/** 
	 * ������Ϣ���ͣ�����
	 *
	 */ 
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	/** 
	 * ������Ϣ���ͣ�ͼ�� 
	 * 
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news"; 
	/**  
	 *  ������Ϣ���ͣ��ı� 
	 *
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	/** 
	 * ������Ϣ���ͣ�ͼƬ
	 *  
	 */ 
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	/** 
	 *  ������Ϣ���ͣ����� 
	 *   
	 */ 
	public static final String REQ_MESSAGE_TYPE_LINK = "link"; 
	/** 
	 * ������Ϣ���ͣ�����λ��
	 *  
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location"; 
	/**
	 * ������Ϣ���ͣ���Ƶ
	 *  
	 */ 
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice"; 
	 /** 
	  * ������Ϣ���ͣ�����
	  *  
	  */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event"; 
	/** 
	 * �¼����ͣ�subscribe(����)
	 *  
	 */ 
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe"; 
	/**
	 * �¼����ͣ�unsubscribe(ȡ������)
	 *  
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	/**
	 * �¼����ͣ�CLICK(�Զ���˵�����¼�)
	 *  
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	//��request ��Ϣ ת���� ������Ϣ����
	public static MsgRequest parseXml(HttpServletRequest request) throws Exception {
		MsgRequest msgReq = new MsgRequest();
		
		// ����XML
		InputStream inputStream = request.getInputStream();
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		
		// �����ڵ㣬��װ�ɶ���
		for (Element e : elementList){
			String name = e.getName();
			String text = e.getText();
			
			if("MsgType".equals(name)){//��Ϣ����
				msgReq.setMsgType(text);
			}else if("MsgId".equals(name)){
				msgReq.setMsgId(text);
			}else if("FromUserName".equals(name)){
				msgReq.setFromUserName(text);
			}else if("ToUserName".equals(name)){
				msgReq.setToUserName(text);
			}else if("CreateTime".equals(name)){
				msgReq.setCreateTime(text);
			}else if("Content".equals(name)){//�ı���Ϣ
				msgReq.setContent(text);
			}else if("PicUrl".equals(name)){//ͼƬ��Ϣ
				msgReq.setPicUrl(text);
			}else if("Location_X".equals(name)){//����λ����Ϣ
				msgReq.setLocation_X(text);
			}else if("Location_Y".equals(name)){
				msgReq.setLocation_Y(text);
			}else if("Scale".equals(name)){
				msgReq.setScale(text);
			}else if("Label".equals(name)){
				msgReq.setLabel(text);
			}else if("Event".equals(name)){//�¼���Ϣ
				msgReq.setEvent(text);
			}else if("EventKey".equals(name)){
				msgReq.setEventKey(text);
			}
		}
		inputStream.close();
		inputStream = null;
		return msgReq;
	}
	
	
	/**
	 * �ı���Ϣת����xml
	 * 
	 * @param text
	 * @return
	 */
	public static String textToXml(MsgResponseText text) {
		xstream.alias("xml", text.getClass());
		return xstream.toXML(text);
	}
	
	
	/**
	 * ͼ����Ϣת����xml
	 * 
	 * @param news
	 * @return
	 */
	public static String newsToXml(MsgResponseNews news) {
		xstream.alias("xml", news.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(news);
	}
	
	/**
	 * ��չxstream����xml�ڵ�����CDATA���
	 */
	public static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean CDATA = true;
				
				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}
				protected void writeText(QuickWriter writer, String text) {
					if (CDATA) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
	
}


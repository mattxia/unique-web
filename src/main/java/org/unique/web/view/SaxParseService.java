package org.unique.web.view;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 使用SAX对XML进行解析替代DOM4J或者JDOM，减少包依赖
 * 
 * @author yong
 * @date 2010-9-19
 * @version 1.0
 */
public class SaxParseService extends DefaultHandler {
	private Map<String, String> hashMap = null;
	private String preTag = null;// 作用是记录解析时的上一个节点名称
	private ServletUrl book;

	private static final String SERVLET_NODE = "servlet";
	private static final String CLASS_NODE = "class";
	private static final String URL_NODE = "url";

	public Map<String, String> getResult(InputStream xmlStream) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(xmlStream, this);

		return hashMap;
	}

	@Override
	public void startDocument() throws SAXException {
		hashMap = new HashMap<String, String>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (SERVLET_NODE.equals(qName)) {
			book = new ServletUrl();
		}

		preTag = qName;// 将正在解析的节点名称赋给preTag
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (SERVLET_NODE.equals(qName)) {
			
			if(!book.getUrls().isEmpty()){			
				for(String url : book.getUrls()){
					hashMap.put(url, book.getPath());
				}
			}

			book = null;
		}

		preTag = null;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (preTag != null) {
			String content = new String(ch, start, length);
			if (CLASS_NODE.equals(preTag)) {
				book.setPath(content);
			} else if (URL_NODE.equals(preTag)) {
				book.setUrl(content);
			}
		}
	}
	
	public static void main(String [] args) throws Exception{
		SaxParseService sax = new SaxParseService();  
        InputStream input = SaxParseService.class.getClassLoader().getResourceAsStream("servlets.xml");  
        Map<String, String> hashMap = sax.getResult(input);
        
        if(hashMap == null){
        	System.out.println("xml is null");
        	return;
        }
        
        for(Map.Entry<String, String> entry : hashMap.entrySet()){
        	System.out.println(entry.getValue() + ":" + entry.getKey());
        }
	}
}

/**
 * 定义一个Servlet和URL对应BEAN,用以记录解析XML内容
 * @author rex
 */
class ServletUrl {
	/**
	 * servlet class 路径
	 */
	private String path;
	private List<String> urls;
	
	public ServletUrl(){
		urls = new ArrayList<String>();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrl(String url) {
		this.urls.add(url);
	}
}
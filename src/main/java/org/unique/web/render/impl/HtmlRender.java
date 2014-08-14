package org.unique.web.render.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unique.common.tools.IOUtil;
import org.unique.web.core.Const;
import org.unique.web.render.Render;

/**
 * HtmlRender.
 */
public class HtmlRender implements Render {
	
	private static final String contentType = "text/html;charset=" + Const.ENCODING;
	private String text;
	
	public HtmlRender(String text) {
		this.text = text;
	}
	
	public void render(HttpServletRequest request, HttpServletResponse response, String viewPath) {
		PrintWriter writer = null;
		try {
			response.setHeader("Pragma", "no-cache");	// HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expires", 0);
	        
			response.setContentType(contentType);
	        writer = response.getWriter();
	        writer.write(text);
	        writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			IOUtil.closeQuietly(writer);
		}
	}
}





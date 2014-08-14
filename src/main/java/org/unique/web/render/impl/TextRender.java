package org.unique.web.render.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unique.common.tools.IOUtil;
import org.unique.web.core.Const;
import org.unique.web.render.Render;

/**
 * TextRender.
 */
public class TextRender implements Render {
	
	private static final String defaultContentType = "text/plain;charset=" + Const.ENCODING;
	private String text;
	
	public TextRender(String text) {
		this.text = text;
	}
	
	private String contentType;
	public TextRender(String text, String contentType) {
		this.text = text;
		this.contentType = contentType;
	}
	
	public void render(HttpServletRequest request, HttpServletResponse response, String viewPath) {
		PrintWriter writer = null;
		try {
			response.setHeader("Pragma", "no-cache");	// HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expires", 0);
	        
	        if (contentType == null) {
	        	response.setContentType(defaultContentType);
	        }
	        else {
	        	response.setContentType(contentType);
				response.setCharacterEncoding(Const.ENCODING);
	        }
	        
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




